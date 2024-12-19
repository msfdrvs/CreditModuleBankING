package com.mustafa.ing.service;

import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.repository.LoanInstallmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoanInstallmentUpdateServiceTest {

    @InjectMocks
    private LoanInstallmentUpdateService loanInstallmentUpdateService;

    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    @Test
    void shouldSaveLoanInstallment() {
        //given
        List<LoanInstallment> loanInstallments = List.of(LoanInstallment.builder()
                .amount(BigDecimal.valueOf(10000))
                .loanId(1)
                .isPaid(false)
                .build());

        //when
        loanInstallmentUpdateService.updateLoanInstallments(loanInstallments);

        //then
        verify(loanInstallmentRepository, times(1)).saveAll(anyList());
    }
}