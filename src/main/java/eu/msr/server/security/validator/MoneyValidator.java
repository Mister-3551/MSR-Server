package eu.msr.server.security.validator;

import eu.msr.server.security.impl.ConstraintViolation;
import eu.msr.server.security.validator.anno.ValidMoney;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class MoneyValidator implements ConstraintValidator<ValidMoney, String>, ConstraintViolation {

    @Override
    public void initialize(ValidMoney constraintAnnotation) {
    }

    @Override
    public boolean isValid(String money, ConstraintValidatorContext constraintValidatorContext) {
        return money.matches("^[0-9.]+$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}