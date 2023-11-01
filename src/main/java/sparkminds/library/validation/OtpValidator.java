package sparkminds.library.validation;

import java.time.Instant;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sparkminds.library.entities.OneTimePassword;
import sparkminds.library.exception.OtpExpiredTime;
import sparkminds.library.exception.UnKnownOtp;
import sparkminds.library.repository.OntimePasswordRepository;
import sparkminds.library.validation.annotation.OtpConstraint;

@Component
@RequiredArgsConstructor
public class OtpValidator implements ConstraintValidator<OtpConstraint, String> {

    private final OntimePasswordRepository ontimePasswordRepository;

    @Override
    public void initialize(OtpConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String otp, ConstraintValidatorContext constraintValidatorContext) {
        Optional<OneTimePassword> optionalOneTimePassword = ontimePasswordRepository.findByOtp(otp);
        if (optionalOneTimePassword.isPresent()) {
            Instant expiredTime = optionalOneTimePassword.get().getDateOfExpiry();
            Instant currentTime = Instant.now();
            int comparisonResult = expiredTime.compareTo(currentTime);
            if (comparisonResult < 0) {
                throw new OtpExpiredTime("OTP is expired");
            } else {
                return true;
            }
        }  else {
            throw new UnKnownOtp("OTP doesn't exist");
        }
    }
}
