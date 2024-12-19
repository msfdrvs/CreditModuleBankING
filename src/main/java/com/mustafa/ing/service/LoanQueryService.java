package com.mustafa.ing.service;

import com.mustafa.ing.exception.RecordNotFoundException;
import com.mustafa.ing.exception.UnauthorizedOperationException;
import com.mustafa.ing.model.Customer;
import com.mustafa.ing.model.Loan;
import com.mustafa.ing.repository.LoanRepository;
import com.mustafa.ing.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanQueryService {

    private final LoanRepository loanRepository;

    private final CustomerQueryService customerQueryService;

    public List<Loan> findLoanByCustomerId(int customerId) throws RecordNotFoundException {
        try {
            Customer customer = customerQueryService.findCustomerById(customerId);
            validateFindLoan(customer);

            return loanRepository.findAllByCustomerIdOrderByCreateDateAsc(customerId);
        } catch (Exception ex) {
            log.error("Kredi bilgileri getirilirken bir hata olustu.", ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Loan findLoanById(int loanId){
        return loanRepository.findById(loanId).orElseThrow(RecordNotFoundException::new);
    }

    private void validateFindLoan(Customer customer) {
        if (!UserUtils.validateUser(customer)) {
            throw new UnauthorizedOperationException();
        }
    }
}
