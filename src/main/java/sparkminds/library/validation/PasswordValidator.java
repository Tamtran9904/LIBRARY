package sparkminds.library.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import sparkminds.library.exception.DataInValidException;
import sparkminds.library.validation.annotation.PasswordConstraint;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            return true;
        } else {
            throw new DataInValidException("Password Invalid");
        }
    }
}
