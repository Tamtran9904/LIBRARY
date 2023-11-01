package sparkminds.library.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import sparkminds.library.exception.InValidPasswordRegex;
import sparkminds.library.validation.annotation.EmailConstraint;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email != null && email.matches("^([a-z0-9_.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$")) {
            return true;
        } else {
            throw new InValidPasswordRegex("Password Invalid");
        }
    }
}
