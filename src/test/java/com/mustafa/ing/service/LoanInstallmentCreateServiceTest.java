package com.mustafa.ing.service;

import com.mustafa.ing.model.Loan;
import com.mustafa.ing.repository.LoanInstallmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoanInstallmentCreateServiceTest {
    @InjectMocks
    private LoanInstallmentCreateService loanInstallmentCreateService;

    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    @Test
    void shouldCreateLoanInstallments() {
        //given
        Loan loan = Loan.builder()
                .id(1)
                .isPaid(false)
                .loanAmount(BigDecimal.valueOf(100000))
                .numberOfInstallments(6)
                .createDate(new Date())
                .build();

        //when
        loanInstallmentCreateService.createLoanInstallments(loan);

        //then
        verify(loanInstallmentRepository, times(1)).saveAll(anyList());

    }


}