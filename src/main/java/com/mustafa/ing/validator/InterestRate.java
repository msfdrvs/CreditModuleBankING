package com.mustafa.ing.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = InterestRateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InterestRate {
    String message() default "Uygun bir faiz oranı giriniz (0.1 - 0.5)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
