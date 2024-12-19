package com.mustafa.ing.dto;

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
public class PayLoanRequest {

    @NotNull
    private int loanId;

    @NotNull
    private BigDecimal amount;
}
