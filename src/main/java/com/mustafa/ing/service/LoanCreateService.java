package com.mustafa.ing.service;

import com.mustafa.ing.dto.CreateLoanRequest;
import com.mustafa.ing.dto.CreateLoanResponse;
import com.mustafa.ing.exception.UnauthorizedOperationException;
import com.mustafa.ing.mapper.LoanMapper;
import com.mustafa.ing.model.Customer;
import com.mustafa.ing.model.Loan;
import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.repository.LoanRepository;
import com.mustafa.ing.util.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanCreateService {

    private final LoanRepository loanRepository;

    private final CustomerQueryService customerQueryService;

    private final LoanInstallmentCreateService loanInstallmentCreateService;

    private final CustomerUpdateService customerUpdateService;

    private final LoanMapper loanMapper;

    @Transactional
    public CreateLoanResponse createLoan(CreateLoanRequest request) {

        try {
            Customer customer = customerQueryService.findCustomerById(request.getCustomerId());

            validateCreateLoan(request, customer);

            Loan loan = loanRepository.save(loanMapper.map(request));
            List<LoanInstallment> loanInstallments = loanInstallmentCreateService.createLoanInstallments(loan);

            customer.setUsedCreditLimit(customer.getUsedCreditLimit().add(request.getAmount()));
            customerUpdateService.updateCustomer(customer);

            return CreateLoanResponse.builder()
                    .loanAmount(loan.getLoanAmount())
                    .loanId(loan.getId())
                    .loanInstallments(loanInstallments)
                    .build();
        } catch (Exception ex) {
            log.error("Kredi oluşturma sırasında bir hata olustu.", ex);
            throw new RuntimeException(ex.getMessage());
        }

    }

    private void validateCreateLoan(CreateLoanRequest request, Customer customer) throws BadRequestException {
        if (!UserUtils.validateUser(customer)) {
            throw new UnauthorizedOperationException();
        }

        if (request.getAmount().compareTo(customer.getCreditLimit().subtract(customer.getUsedCreditLimit())) > 0) {
            throw new BadRequestException("Kredi limiti yetersiz.");
        }
    }
}
