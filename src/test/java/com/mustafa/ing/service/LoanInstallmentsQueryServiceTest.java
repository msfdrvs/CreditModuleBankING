package com.mustafa.ing.service;


import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.repository.LoanInstallmentRepository;
import com.mustafa.ing.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanInstallmentsQueryServiceTest {

    @InjectMocks
    private LoanInstallmentsQueryService loanInstallmentsQueryService;

    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    @Mock
    private CustomerQueryService customerQueryService;

    @Test
    void shouldGetUnPaidLoanInstallmentsByLoanId() {
        //given
        int loanId = 1;

        List<LoanInstallment> loanInstallments = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            loanInstallments.add(LoanInstallment.builder()
                    .dueDate(DateUtils.getNextNthInstallmentDueDate(i))
                    .amount(BigDecimal.valueOf(16666))
                    .loanId(1)
                    .isPaid(false)
                    .build());
        }

        when(loanInstallmentRepository.findAllByLoanIdAndIsPaidIsFalseOrderByDueDateAsc(anyInt())).thenReturn(loanInstallments);

        //when
        loanInstallmentsQueryService.getUnPaidLoanInstallmentsByLoanId(loanId);

        //then
        verify(loanInstallmentRepository, times(1)).findAllByLoanIdAndIsPaidIsFalseOrderByDueDateAsc(anyInt());
    }

}