package com.mustafa.ing.dto;

import com.mustafa.ing.validator.InterestRate;
import com.mustafa.ing.validator.NumberOfInstallment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanRequest {

    @NotNull
    private int customerId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @InterestRate
    private float interestRate;

    @NotNull
    @NumberOfInstallment
    private int numberOfInstallments;
}
