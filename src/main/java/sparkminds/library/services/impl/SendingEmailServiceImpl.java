package sparkminds.library.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Person;
import sparkminds.library.repository.PersonRepository;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Slf4j
public class SendingEmailServiceImpl {

    @Value("${FROM_EMAIL}")
    private String fromAddress;

    @Value("${SEVER_NAME}")
    private String severName;

    private final VerificationLinkServiceImpl verificationLinkService;

    private final OneTimePasswordServiceImpl oneTimePasswordService;

    private final PersonRepository personRepository;

    private final JavaMailSender mailSender;

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

    public void sendVerificationEmail(Person person, String siteURL)
        throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration from LIBRARY";
        MimeMessageHelper helper = generateEmail(person, subject);
        String verifyCode = verificationLinkService.generate(person).getToken();
        String id = person.getId().toString();
        String otp = oneTimePasswordService.generateOTP(person).getOtp();
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
        try {
            mailSender.send(helper.getMimeMessage());
        } catch (MailException exception) {
            log.error("Can not send email");
        }
    }

    public Void reSendOtp(Long id, HttpServletRequest request)
        throws MessagingException, UnsupportedEncodingException {
        String siteURL = request.getRequestURL().toString();
        String subject = "RESEND: NEW TOKEN";
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            MimeMessageHelper helper = generateEmail(person, subject);
            String otp = oneTimePasswordService.generateOTP(person).getOtp();
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
            try {
                mailSender.send(helper.getMimeMessage());
            } catch (MailException exception) {
                log.error("Can not send email");
            }
        }
        return null;
    }
}
