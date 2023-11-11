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
public @interface ValidTime {

    String message() default "Time must be in right format";
    String lengthMessage() default "Time must not be null";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}