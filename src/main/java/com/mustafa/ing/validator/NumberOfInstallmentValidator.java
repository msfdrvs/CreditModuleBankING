package com.mustafa.ing.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NumberOfInstallmentValidator implements ConstraintValidator<NumberOfInstallment, Integer> {
    @Override
    public boolean isValid(Integer numberOfInstallment, ConstraintValidatorContext constraintValidatorContext) {
        return List.of(6, 9, 12, 24).contains(numberOfInstallment);
    }
}
