package eu.msr.server.security.validator;

import eu.msr.server.record.NewPassword;
import eu.msr.server.record.SignUpRequest;
import eu.msr.server.security.impl.ConstraintViolation;
import eu.msr.server.security.validator.anno.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, Object>, ConstraintViolation {

    private String lengthMessage;
    private String uppercaseMessage;
    private String lowercaseMessage;
    private String numbersMessage;
    private String specialCharacterMessage;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.uppercaseMessage = constraintAnnotation.uppercaseMessage();
        this.lowercaseMessage = constraintAnnotation.lowercaseMessage();
        this.numbersMessage = constraintAnnotation.numbersMessage();
        this.specialCharacterMessage = constraintAnnotation.specialCharacterMessage();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext){

        String password = "";
        String confirmPassword = "";

        if (object instanceof SignUpRequest signUpRequest) {
            password = signUpRequest.password();
            confirmPassword = signUpRequest.confirmPassword();
        } else if (object instanceof NewPassword newPassword) {
            password = newPassword.password();
            confirmPassword = newPassword.confirmPassword();
        }

        if (password.length() < 8 || confirmPassword.length() > 16) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            constraintViolation(constraintValidatorContext, uppercaseMessage);
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            constraintViolation(constraintValidatorContext, lowercaseMessage);
            return false;
        }

        if (!password.matches(".*[0-9].*")) {
            constraintViolation(constraintValidatorContext, numbersMessage);
            return false;
        }

        if (!password.matches(".*[!@#$%^&*()].*")) {
            constraintViolation(constraintValidatorContext, specialCharacterMessage);
            return false;
        }
        return password.equals(confirmPassword);
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}