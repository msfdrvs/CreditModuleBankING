package com.mustafa.ing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayLoanResponse {

    private int paidInstallmentCount;

    private BigDecimal totalPaymentAmount;

    private boolean loanPaidCompletely;
}
