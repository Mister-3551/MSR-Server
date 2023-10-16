package eu.msr.server.security.validator;

import eu.msr.server.security.impl.ConstraintViolation;
import eu.msr.server.security.validator.anno.ValidEmailAddress;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressValidator implements ConstraintValidator<ValidEmailAddress, String>, ConstraintViolation {

    @Override
    public void initialize(ValidEmailAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(String emailAddress, ConstraintValidatorContext constraintValidatorContext){
        if (emailAddress == null) {
            return false;
        }
        return emailAddress.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}