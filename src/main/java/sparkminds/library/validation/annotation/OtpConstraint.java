package sparkminds.library.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import sparkminds.library.validation.OtpValidator;

@Documented
@Constraint(validatedBy = OtpValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OtpConstraint {

    String message() default "Invalid Otp";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
