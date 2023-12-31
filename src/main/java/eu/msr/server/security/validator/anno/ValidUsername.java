package eu.msr.server.security.validator.anno;

import com.nimbusds.jose.Payload;
import eu.msr.server.security.validator.UsernameValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface ValidUsername {

    String message() default "Username contains illegal characters";
    String lengthMessage() default "Username must be between 4 and 16 characters long";
    String uppercaseMessage() default "Username must not contains uppercase letters";
    String specialCharactersMessage() default "Username must not contains special characters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}