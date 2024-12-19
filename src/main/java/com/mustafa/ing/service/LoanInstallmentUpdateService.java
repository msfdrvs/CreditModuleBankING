package com.mustafa.ing.service;

import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.repository.LoanInstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanInstallmentUpdateService {

    private final LoanInstallmentRepository loanInstallmentRepository;

    public void updateLoanInstallments(List<LoanInstallment> loanInstallments) {
        loanInstallmentRepository.saveAll(loanInstallments);
    }
}
