package com.mustafa.ing.dto;

import com.mustafa.ing.model.LoanInstallment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanResponse {

    private int loanId;

    private BigDecimal loanAmount;

    private List<LoanInstallment> loanInstallments;
}
