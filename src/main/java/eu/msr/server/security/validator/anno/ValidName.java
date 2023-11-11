package eu.msr.server.security.validator.anno;

import com.nimbusds.jose.Payload;
import eu.msr.server.security.validator.NameValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface ValidName {

    String message() default "Mission name contains illegal characters";
    String lengthMessage() default "Mission name must be between 3 and 30 characters long";
    String specialCharactersMessage() default "Mission name must not contains special characters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}