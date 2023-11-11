package eu.msr.server.security.validator;

import eu.msr.server.security.impl.ConstraintViolation;
import eu.msr.server.security.validator.anno.ValidTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class TimeValidator implements ConstraintValidator<ValidTime, String>, ConstraintViolation {

    @Override
    public void initialize(ValidTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(String time, ConstraintValidatorContext constraintValidatorContext) {
        return time.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}