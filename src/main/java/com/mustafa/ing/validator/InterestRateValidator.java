package com.mustafa.ing.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InterestRateValidator implements ConstraintValidator<InterestRate, Float> {
    @Override
    public boolean isValid(Float interestRate, ConstraintValidatorContext constraintValidatorContext) {
        return (interestRate >= 0.1 && interestRate <= 0.5);
    }
}
