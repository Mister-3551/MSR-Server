package eu.msr.server.security.validator.anno;

import com.nimbusds.jose.Payload;
import eu.msr.server.security.validator.DescriptionValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DescriptionValidator.class)
@Documented
public @interface ValidDescription {

    String message() default "Mission description contains illegal characters";
    String lengthMessage() default "Mission description must be between 3 and 1000 characters long";
    String specialCharactersMessage() default "Mission description must not contains special characters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}