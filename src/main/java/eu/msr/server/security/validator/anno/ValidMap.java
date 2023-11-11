package eu.msr.server.security.validator.anno;

import com.nimbusds.jose.Payload;
import eu.msr.server.security.validator.MapValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MapValidator.class)
@Documented
public @interface ValidMap {

    String message() default "Wrong map format";
    String lengthMessage() default "Map size is more than 2 MB";
    String charactersMessage() default "Map contains invalid Characters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}