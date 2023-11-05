package sparkminds.library.services.impl;

import java.time.Instant;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sparkminds.library.dto.response.RegisterResponse;
import sparkminds.library.entities.OneTimePassword;
import sparkminds.library.entities.Person;
import sparkminds.library.enums.AccountStatus;
import sparkminds.library.enums.TargetToken;
import sparkminds.library.repository.OntimePasswordRepository;
import sparkminds.library.repository.PersonRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OneTimePasswordServiceImpl {

    @Value("${OTP_EXPIRED}")
    private Integer otpTime;

    private final OntimePasswordRepository ontimePasswordRepository;

    private final HandleAfterVerifyServiceImpl handleAfterVerifyService;

    private final PersonRepository personRepository;

    @Transactional(rollbackOn = Exception.class)
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

    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse enterOtp(String otp, String email) {
        Optional<OneTimePassword> optionalOneTimePassword = ontimePasswordRepository.findByOtp(otp);
        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if (optionalOneTimePassword.isPresent()) {
            OneTimePassword oneTimePassword = optionalOneTimePassword.get();
            if (optionalPerson.isPresent()) {
                Person person = optionalPerson.get();
                if (oneTimePassword.getPersonId().equals(person)) {
                    person.setAccountStatus(AccountStatus.ACTIVE);
                    handleAfterVerifyService.deleteAfterVerify(person);
                    personRepository.save(person);
                }
            }
        }
        return RegisterResponse.builder().status(HttpStatus.OK).message("Verification Success")
            .build();
    }

}
