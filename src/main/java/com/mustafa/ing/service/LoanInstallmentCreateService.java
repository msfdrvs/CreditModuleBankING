package com.mustafa.ing.service;

import com.mustafa.ing.model.Loan;
import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.repository.LoanInstallmentRepository;
import com.mustafa.ing.util.CalculationUtils;
import com.mustafa.ing.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanInstallmentCreateService {

    private final LoanInstallmentRepository loanInstallmentRepository;

    public List<LoanInstallment> createLoanInstallments(Loan loan) {
        List<LoanInstallment> loanInstallments = new ArrayList<>();
        BigDecimal installmentAmount = CalculationUtils.calculateInstallmentAmount(loan.getLoanAmount(), loan.getNumberOfInstallments());
        BigDecimal remainderAmount = loan.getLoanAmount().subtract(installmentAmount.multiply(BigDecimal.valueOf(loan.getNumberOfInstallments())));

        for (int i = 0; i < loan.getNumberOfInstallments(); i++) {
            loanInstallments.add(LoanInstallment.builder()
                    .loanId(loan.getId())
                    .amount(i == 0 ? installmentAmount.add(remainderAmount) : installmentAmount)
                    .dueDate(DateUtils.getNextNthInstallmentDueDate(i))
                    .paidAmount(BigDecimal.ZERO)
                    .isPaid(false)
                    .build());
        }

        return loanInstallmentRepository.saveAll(loanInstallments);
    }
}
