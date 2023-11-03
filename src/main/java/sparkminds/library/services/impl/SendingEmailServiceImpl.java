package sparkminds.library.services.impl;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sparkminds.library.dto.response.RegisterResponse;
import sparkminds.library.entities.OneTimePassword;
import sparkminds.library.entities.Person;
import sparkminds.library.entities.VerificationToken;
import sparkminds.library.enums.AccountStatus;
import sparkminds.library.enums.TargetToken;
import sparkminds.library.enums.VerificationTokenStatus;
import sparkminds.library.exception.UnKnownVerificationToken;
import sparkminds.library.exception.UnknownPersonAccount;
import sparkminds.library.exception.VerifyLinkExpiredTime;
import sparkminds.library.repository.OntimePasswordRepository;
import sparkminds.library.repository.PersonRepository;
import sparkminds.library.repository.VerificationTokenRepository;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Slf4j
public class SendingEmailServiceImpl {

    @Value("${FROM_EMAIL}")
    private String fromAddress;

    @Value("${OTP_EXPIRED}")
    private Integer otpTime;

    @Value("${VERIFY_CODE_EXPIRED}")
    private Integer verificationTokenTime;

    @Value("${SEVER_NAME}")
    private String severName;

    private final VerificationTokenRepository verificationTokenRepository;

    private final OntimePasswordRepository ontimePasswordRepository;

    private final PersonRepository personRepository;

    private final JavaMailSender mailSender;

    public VerificationToken generate(Person person) {
        verificationTokenRepository.findAll()
            .stream()
            .filter(
                verificationToken -> verificationToken.getDateOfExpiry().compareTo(Instant.now())
                    < 0)
            .forEach(verificationTokenRepository::delete);
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setVerificationTokenStatus(VerificationTokenStatus.NON_VERIFIED);
        verificationToken.setDateOfExpiry(
            Instant.from(Instant.now()).plusSeconds(verificationTokenTime));
        verificationToken.setPersonId(person);
        verificationToken.setTargetToken(TargetToken.REGISTER_ACCOUNT);

        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    public OneTimePassword generateOTP(Person person) {
        person.getOneTimePasswordList()
            .stream()
            .filter(oneTimePassword -> oneTimePassword.getTargetToken()
                .equals(TargetToken.REGISTER_ACCOUNT))
            .forEach(ontimePasswordRepository::delete);

        String otp = RandomString.make(6);
        OneTimePassword oneTimePassword = new OneTimePassword();
        oneTimePassword.setOtp(otp);
        oneTimePassword.setDateOfExpiry(Instant.from(Instant.now()).plusSeconds(otpTime));
        oneTimePassword.setPersonId(person);
        oneTimePassword.setTargetToken(TargetToken.REGISTER_ACCOUNT);

        ontimePasswordRepository.save(oneTimePassword);

        return oneTimePassword;
    }

    public void sendVerificationEmail(Person person, String siteURL)
        throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration from LIBRARY";
        MimeMessageHelper helper = generateEmail(person, subject);
        String verifyCode = generate(person).getToken();
        String id = person.getId().toString();
        String otp = generateOTP(person).getOtp();
        String content = "Dear [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "<br>"
            + "Or use OTP to login:</p>"
            + "<p><b>" + otp + "</b></p>"
            + "Note: this OTP is set to expire in 1 minuets"
            + "<br>"
            + "If you not see OTP, please click here to resend OTP:"
            + "<h3><a href=\"[[RESEND]]\" target=\"_self\">RESEND</a></h3>"
            + "Thank you,<br>"
            + severName;
        content = content.replace("[[name]]", person.getName());
        String verifyURL = siteURL + "/verify?code=" + verifyCode + "&id=" + id;
        content = content.replace("[[URL]]", verifyURL);
        String resendURL = siteURL + "/reSend?id=" + id;
        content = content.replace("[[RESEND]]", resendURL);
        helper.setText(content, true);

        mailSender.send(helper.getMimeMessage());
    }

    public MimeMessageHelper generateEmail(Person person, String subject)
        throws MessagingException, UnsupportedEncodingException {
        String toAddress = person.getEmail();
        String senderName = "LIBRARY";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        return helper;
    }

    public Void reSendOtp(Long id, HttpServletRequest request)
        throws MessagingException, UnsupportedEncodingException {
        String siteURL = request.getRequestURL().toString();
        String subject = "RESEND: NEW TOKEN";
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            MimeMessageHelper helper = generateEmail(person, subject);
            String otp = generateOTP(person).getOtp();
            String content = "Dear [[name]],<br>"
                + "YOUR NEW OTP</p>"
                + "<p><b>" + otp + "</b></p>"
                + "Note: this OTP is set to expire in 1 minuets"
                + "<br>"
                + "If you not see OTP, please click here to resend OTP:"
                + "<h3><a href=\"[[RESEND]]\" target=\"_self\">RESEND</a></h3>"
                + "Thank you,<br>"
                + severName;
            content = content.replace("[[name]]", person.getName());
            String resendURL = siteURL + "/reSend?id=" + id;
            content = content.replace("[[RESEND]]", resendURL);
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
        }
        return null;
    }

    public RegisterResponse isVerificationToken(String token, Long id) {
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByToken(
            token);
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalVerificationToken.isPresent()) {
            VerificationToken verificationToken = optionalVerificationToken.get();
            if (Boolean.TRUE.equals(isExpiredTime(verificationToken))) {
                if (optionalPerson.isPresent()) {
                    Person person = optionalPerson.get();
                    if (verificationToken.getPersonId().equals(person)) {
                        verificationToken.setVerificationTokenStatus(
                            VerificationTokenStatus.VERIFIED);
                        person.setAccountStatus(AccountStatus.ACTIVE);
                        deleteAfterVerify(person);
                        personRepository.save(person);
                    }
                } else {
                    log.error("Person account doesn't exist");
                    throw new UnknownPersonAccount("Person account doesn't exist");
                }
            }
        } else {
            log.error("Verification token doesn't exist");
            throw new UnKnownVerificationToken("Verification token doesn't exist");
        }
        return RegisterResponse.builder().status(HttpStatus.OK).message("Verification Success")
            .build();
    }

    public RegisterResponse enterOtp(String otp, String email) {
        Optional<OneTimePassword> optionalOneTimePassword = ontimePasswordRepository.findByOtp(otp);
        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if (optionalOneTimePassword.isPresent()) {
            OneTimePassword oneTimePassword = optionalOneTimePassword.get();
            if (optionalPerson.isPresent()) {
                Person person = optionalPerson.get();
                if (oneTimePassword.getPersonId().equals(person)) {
                    person.setAccountStatus(AccountStatus.ACTIVE);
                    deleteAfterVerify(person);
                    personRepository.save(person);
                }
            }
        }
        return RegisterResponse.builder().status(HttpStatus.OK).message("Verification Success")
            .build();
    }

    public void deleteAfterVerify(Person person) {
        person.getVerificationTokenList()
            .stream()
            .filter(verificationToken -> verificationToken.getTargetToken()
                .equals(TargetToken.REGISTER_ACCOUNT))
            .forEach(verificationTokenRepository::delete);

        person.getOneTimePasswordList()
            .stream()
            .filter(oneTimePassword -> oneTimePassword.getTargetToken()
                .equals(TargetToken.REGISTER_ACCOUNT))
            .forEach(ontimePasswordRepository::delete);
    }

    public Boolean isExpiredTime(VerificationToken verificationToken) {
        Instant expiredTime = verificationToken.getDateOfExpiry();
        Instant currentTime = Instant.now();
        int comparisonResult = expiredTime.compareTo(currentTime);
        if (comparisonResult < 0) {
            throw new VerifyLinkExpiredTime("VerifyLink is expired");
        } else {
            return true;
        }
    }
}
