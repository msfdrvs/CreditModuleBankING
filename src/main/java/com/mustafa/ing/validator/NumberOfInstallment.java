package com.mustafa.ing.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = NumberOfInstallmentValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberOfInstallment {
    String message() default "Uygun bir taksit sayısı giriniz (6, 9, 12, 24)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
