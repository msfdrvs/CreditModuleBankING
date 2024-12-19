package com.mustafa.ing.service;

import com.mustafa.ing.model.Loan;
import com.mustafa.ing.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanUpdateService {

    private final LoanRepository loanRepository;

    public void updateLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
