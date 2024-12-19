package com.mustafa.ing.service;


import com.mustafa.ing.model.Loan;
import com.mustafa.ing.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoanUpdateServiceTest {

    @InjectMocks
    private LoanUpdateService loanUpdateService;

    @Mock
    private LoanRepository loanRepository;

    @Test
    void shouldUpdateLoan() {
        //given
        Loan loan = Loan.builder()
                .id(1)
                .loanAmount(BigDecimal.valueOf(100000))
                .numberOfInstallments(6)
                .isPaid(false)
                .createDate(new Date())
                .build();

        //when
        loanUpdateService.updateLoan(loan);

        //then
        verify(loanRepository, times(1)).save(any(Loan.class));
    }



}