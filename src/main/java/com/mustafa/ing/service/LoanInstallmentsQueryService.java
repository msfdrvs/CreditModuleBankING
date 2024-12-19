package com.mustafa.ing.service;

import com.mustafa.ing.exception.RecordNotFoundException;
import com.mustafa.ing.exception.UnauthorizedOperationException;
import com.mustafa.ing.model.Customer;
import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.repository.LoanInstallmentRepository;
import com.mustafa.ing.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanInstallmentsQueryService {

    private final LoanInstallmentRepository loanInstallmentRepository;

    private final CustomerQueryService customerQueryService;

    public List<LoanInstallment> findAllLoanInstallmentsByLoanId(int loanId) throws RecordNotFoundException {
        try {
            Customer customer = customerQueryService.findCustomerByLoanId(loanId);
            validateFindLoanInstallments(customer);

            return loanInstallmentRepository.findAllByLoanId(loanId);
        } catch (Exception ex) {
            log.error("Kredi taksit bilgileri getirilirken bir hata olustu.", ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void validateFindLoanInstallments(Customer customer) {
        if (!UserUtils.validateUser(customer)) {
            throw new UnauthorizedOperationException();
        }
    }

    public List<LoanInstallment> getUnPaidLoanInstallmentsByLoanId(int loanId) {
        return loanInstallmentRepository.findAllByLoanIdAndIsPaidIsFalseOrderByDueDateAsc(loanId);
    }
}
