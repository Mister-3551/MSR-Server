package eu.msr.server.security.validator.anno;

import com.nimbusds.jose.Payload;
import eu.msr.server.security.validator.ImageValidator;
import eu.msr.server.security.validator.UsernameValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
@Documented
public @interface ValidImage {

    String message() default "Wrong image format";
    String lengthMessage() default "Image size is more than 2 MB";
    String charactersMessage() default "Image contains invalid Characters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}