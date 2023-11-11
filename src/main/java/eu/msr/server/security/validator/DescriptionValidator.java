package eu.msr.server.security.validator;

import eu.msr.server.security.impl.ConstraintViolation;
import eu.msr.server.security.validator.anno.ValidDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class DescriptionValidator implements ConstraintValidator<ValidDescription, String>, ConstraintViolation {

    private String lengthMessage;
    private String specialCharacterMessage;

    @Override
    public void initialize(ValidDescription constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.specialCharacterMessage = constraintAnnotation.specialCharactersMessage();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        if (username.length() < 3 || username.length() > 1000) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (username.matches(".*[!@#$%^&*()].*")) {
            constraintViolation(constraintValidatorContext, specialCharacterMessage);
            return false;
        }
        return username.matches("^[a-z0-9]+$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}