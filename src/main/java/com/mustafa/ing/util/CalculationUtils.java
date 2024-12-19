package com.mustafa.ing.util;

import com.mustafa.ing.constant.DefaultConstantValues;
import com.mustafa.ing.model.LoanInstallment;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@UtilityClass
public class CalculationUtils {

    public BigDecimal calculateTotalLoanAmount(BigDecimal loanAmount, float interestRate) {
        return loanAmount.multiply(BigDecimal.valueOf(DefaultConstantValues.LOAN_RATE_CONSTANT)
                        .add(BigDecimal.valueOf(interestRate)).setScale(2, RoundingMode.DOWN))
                .setScale(2, RoundingMode.UNNECESSARY);
    }

    public BigDecimal calculateInstallmentAmount(BigDecimal loanAmount, int numberOfInstallments) {
        return loanAmount.divide(BigDecimal.valueOf(numberOfInstallments), RoundingMode.DOWN)
                .setScale(2, RoundingMode.UNNECESSARY);
    }

    public BigDecimal calculateInstallmentCurrentAmount(LoanInstallment loanInstallment) {
        long dayDiff = DateUtils.getDayDiff(new Date(), loanInstallment.getDueDate());
        return loanInstallment.getAmount().add(
                loanInstallment.getAmount().multiply(BigDecimal.valueOf(DefaultConstantValues.LOAN_INSTALLMENT_DATE_CONSTANT * dayDiff))
                        .setScale(2, RoundingMode.DOWN));
    }

    public BigDecimal calculateTotalInstallmentPaid(List<LoanInstallment> loanInstallments) {
        return loanInstallments.stream()
                .map(LoanInstallment::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
