package sparkminds.library.services.impl;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Person;
import sparkminds.library.enums.TargetToken;
import sparkminds.library.repository.OntimePasswordRepository;
import sparkminds.library.repository.VerificationTokenRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class HandleAfterVerifyServiceImpl {

    private final VerificationTokenRepository verificationTokenRepository;

    private final OntimePasswordRepository ontimePasswordRepository;

    @Transactional(rollbackOn = Exception.class)
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

}
