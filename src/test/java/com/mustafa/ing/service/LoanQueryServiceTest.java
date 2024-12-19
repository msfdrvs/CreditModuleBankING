package com.mustafa.ing.service;


import com.mustafa.ing.exception.RecordNotFoundException;
import com.mustafa.ing.model.Loan;
import com.mustafa.ing.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanQueryServiceTest {

    @InjectMocks
    private LoanQueryService loanQueryService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private CustomerQueryService customerQueryService;

    @Test
    void shouldFindLoanById() {
        //given
        int loanId = 1;

        Loan loan = Loan.builder()
                .id(1)
                .loanAmount(BigDecimal.valueOf(100000))
                .numberOfInstallments(6)
                .isPaid(false)
                .createDate(new Date())
                .build();

        when(loanRepository.findById(anyInt())).thenReturn(Optional.of(loan));

        //when
        loanQueryService.findLoanById(loanId);

        //then
        verify(loanRepository, times(1)).findById(anyInt());

    }

    @Test
    void shouldNotFindLoanById() {
        //given
        int loanId = 1;

        when(loanRepository.findById(anyInt())).thenThrow(new RecordNotFoundException());

        //when
        assertThrows(RecordNotFoundException.class, () -> loanQueryService.findLoanById(loanId));

        //then
        verify(loanRepository, times(1)).findById(anyInt());
    }




}