package com.mustafa.ing.service;

import com.mustafa.ing.dto.PayLoanRequest;
import com.mustafa.ing.dto.PayLoanResponse;
import com.mustafa.ing.exception.UnauthorizedOperationException;
import com.mustafa.ing.model.Customer;
import com.mustafa.ing.model.Loan;
import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.util.CalculationUtils;
import com.mustafa.ing.util.DateUtils;
import com.mustafa.ing.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanCapabilityService {

    private final LoanQueryService loanQueryService;

    private final LoanUpdateService loanUpdateService;

    private final LoanInstallmentsQueryService loanInstallmentsQueryService;

    private final LoanInstallmentUpdateService loanInstallmentUpdateService;

    private final CustomerQueryService customerQueryService;

    private final CustomerUpdateService customerUpdateService;

    @Transactional
    public PayLoanResponse payLoan(PayLoanRequest payLoanRequest) {
        try {
            Customer customer = customerQueryService.findCustomerByLoanId(payLoanRequest.getLoanId());
            Loan loan = loanQueryService.findLoanById(payLoanRequest.getLoanId());

            validatePayLoan(customer, loan);

            List<LoanInstallment> unPaidLoanInstallments = loanInstallmentsQueryService.getUnPaidLoanInstallmentsByLoanId(payLoanRequest.getLoanId());
            List<LoanInstallment> paidLoanInstallments = payLoanInstallments(payLoanRequest.getAmount(), unPaidLoanInstallments);

            if (paidLoanInstallments.isEmpty()) {
                return PayLoanResponse.builder()
                        .loanPaidCompletely(false)
                        .totalPaymentAmount(BigDecimal.ZERO)
                        .paidInstallmentCount(0)
                        .build();
            }

            BigDecimal totalInstallmentPaid = CalculationUtils.calculateTotalInstallmentPaid(paidLoanInstallments);

            loanInstallmentUpdateService.updateLoanInstallments(paidLoanInstallments);

            if (unPaidLoanInstallments.size() == paidLoanInstallments.size()) {
                loan.setIsPaid(true);
                loanUpdateService.updateLoan(loan);
            }

            customer.setUsedCreditLimit(BigDecimal.ZERO.max(customer.getUsedCreditLimit().subtract(totalInstallmentPaid)));
            customerUpdateService.updateCustomer(customer);

            return PayLoanResponse.builder()
                    .loanPaidCompletely(loan.getIsPaid())
                    .totalPaymentAmount(totalInstallmentPaid)
                    .paidInstallmentCount(paidLoanInstallments.size())
                    .build();

        } catch (Exception ex) {
            log.error("Kredi ödemeleri sırasında bir hata olustu.", ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LoanInstallment> payLoanInstallments(BigDecimal amount, List<LoanInstallment> loanInstallments) {
        List<LoanInstallment> paidLoanInstallments = new ArrayList<>();
        BigDecimal remainingAmount = amount.subtract(BigDecimal.ZERO);
        for (LoanInstallment loanInstallment : loanInstallments) {
            BigDecimal currentInstallmentAmount = CalculationUtils.calculateInstallmentCurrentAmount(loanInstallment);
            if (remainingAmount.compareTo(currentInstallmentAmount) >= 0 && DateUtils.inPayableDateRange(loanInstallment.getDueDate())) {
                loanInstallment.setPaymentDate(DateUtils.getCurrentDate());
                loanInstallment.setPaidAmount(currentInstallmentAmount);
                loanInstallment.setIsPaid(true);
                paidLoanInstallments.add(loanInstallment);

                remainingAmount = remainingAmount.subtract(currentInstallmentAmount);
            } else {
                break;
            }
        }
        return paidLoanInstallments;
    }

    public void validatePayLoan(Customer customer, Loan loan) {
        if (!UserUtils.validateUser(customer)) {
            throw new UnauthorizedOperationException();
        }

        if (loan.getIsPaid()) {
            throw new RuntimeException("Kredi ödemeleri tamamlanmış.");
        }
    }
}
