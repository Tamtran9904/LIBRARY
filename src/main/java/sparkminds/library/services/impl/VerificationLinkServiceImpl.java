package sparkminds.library.services.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sparkminds.library.dto.response.RegisterResponse;
import sparkminds.library.entities.Person;
import sparkminds.library.entities.VerificationToken;
import sparkminds.library.enums.AccountStatus;
import sparkminds.library.enums.TargetToken;
import sparkminds.library.enums.VerificationTokenStatus;
import sparkminds.library.exception.DataInValidException;
import sparkminds.library.exception.VerificationLinkInValidException;
import sparkminds.library.repository.PersonRepository;
import sparkminds.library.repository.VerificationTokenRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationLinkServiceImpl {

    @Value("${VERIFY_CODE_EXPIRED}")
    private Integer verificationTokenTime;

    private final VerificationTokenRepository verificationTokenRepository;

    private final HandleAfterVerifyServiceImpl handleAfterVerifyService;

    private final PersonRepository personRepository;

    @Transactional(rollbackOn = Exception.class)
    public VerificationToken generate(Person person) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setVerificationTokenStatus(VerificationTokenStatus.NON_VERIFIED);
        verificationToken.setDateOfExpiry(Instant.from(Instant.now()).plusSeconds(verificationTokenTime));
        verificationToken.setPersonId(person);
        verificationToken.setTargetToken(TargetToken.REGISTER_ACCOUNT);

        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse isVerificationToken(String token, Long id) {
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByToken(token);
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalVerificationToken.isEmpty()) {
            log.error("Verification token doesn't exist");
            throw new VerificationLinkInValidException("Verification token doesn't exist");
        }
        if (optionalPerson.isEmpty()) {
            log.error("Person account doesn't exist");
            throw new DataInValidException("Person account doesn't exist");
        }
        VerificationToken verificationToken = optionalVerificationToken.get();
        Person person = optionalPerson.get();

        isExpiredTime(verificationToken);
        validPerson(verificationToken, person);

        verificationToken.setVerificationTokenStatus(VerificationTokenStatus.VERIFIED);
        person.setAccountStatus(AccountStatus.ACTIVE);
        personRepository.save(person);
        handleAfterVerifyService.deleteAfterVerify(person);
        return RegisterResponse.builder().status(HttpStatus.OK).message("Verification Success").build();
    }

    public void isExpiredTime(VerificationToken verificationToken) {
        Instant expiredTime = verificationToken.getDateOfExpiry();
        Instant currentTime = Instant.now();
        int comparisonResult = expiredTime.compareTo(currentTime);
        if (comparisonResult < 0) {
            throw new VerificationLinkInValidException("VerifyLink is expired");
        }
    }

    public void validPerson(VerificationToken verificationToken, Person person) {
        if (!verificationToken.getPersonId().equals(person)) {
            log.error("User and Verification Token doesn't match");
            throw new DataInValidException("User and Verification Token doesn't match");
        }
    }
}
