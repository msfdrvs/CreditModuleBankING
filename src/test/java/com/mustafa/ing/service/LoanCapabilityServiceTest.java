package com.mustafa.ing.service;

import com.mustafa.ing.dto.PayLoanRequest;
import com.mustafa.ing.dto.PayLoanResponse;
import com.mustafa.ing.model.Customer;
import com.mustafa.ing.model.Loan;
import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.util.DateUtils;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanCapabilityServiceTest {

    @InjectMocks
    private LoanCapabilityService loanCapabilityService;

    @Mock
    private LoanQueryService loanQueryService;

    @Mock
    private LoanUpdateService loanUpdateService;

    @Mock
    private LoanInstallmentsQueryService loanInstallmentsQueryService;

    @Mock
    private LoanInstallmentUpdateService loanInstallmentUpdateService;

    @Mock
    private CustomerQueryService customerQueryService;

    @Mock
    private CustomerUpdateService customerUpdateService;

    @Before("")
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @SneakyThrows
    void shouldNotPayLoan() {
        //given
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        PayLoanRequest payLoanRequest = PayLoanRequest.builder()
                .loanId(1)
                .amount(BigDecimal.valueOf(40000))
                .build();

        Customer customer = Customer.builder()
                .id(1)
                .username("ahmet")
                .usedCreditLimit(BigDecimal.valueOf(100000))
                .build();

        Loan loan = Loan.builder()
                .id(1)
                .loanAmount(BigDecimal.valueOf(100000))
                .numberOfInstallments(6)
                .isPaid(false)
                .createDate(dateFormat.parse("11.12.2024"))
                .build();

        List<LoanInstallment> loanInstallments = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            loanInstallments.add(LoanInstallment.builder()
                            .dueDate(DateUtils.getNextNthInstallmentDueDate(i))
                            .amount(BigDecimal.valueOf(16666))
                            .loanId(1)
                            .isPaid(false)
                    .build());
        }

        when(customerQueryService.findCustomerByLoanId(anyInt())).thenReturn(customer);
        when(loanQueryService.findLoanById(anyInt())).thenReturn(loan);

        UserDetails applicationUser = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);

        //when
        assertThrows(RuntimeException.class, () -> loanCapabilityService.payLoan(payLoanRequest)) ;

        //then
        verify(loanInstallmentUpdateService, times(0)).updateLoanInstallments(anyList());
        verify(customerUpdateService, times(0)).updateCustomer(any(Customer.class));
    }

    @Test
    @SneakyThrows
    void shouldPayLoan() {
        //given
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        PayLoanRequest payLoanRequest = PayLoanRequest.builder()
                .loanId(1)
                .amount(BigDecimal.valueOf(40000))
                .build();

        Customer customer = Customer.builder()
                .id(1)
                .username("ahmet")
                .usedCreditLimit(BigDecimal.valueOf(100000))
                .build();

        Loan loan = Loan.builder()
                .id(1)
                .loanAmount(BigDecimal.valueOf(100000))
                .numberOfInstallments(6)
                .isPaid(false)
                .createDate(dateFormat.parse("11.12.2024"))
                .build();

        List<LoanInstallment> loanInstallments = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            loanInstallments.add(LoanInstallment.builder()
                    .dueDate(DateUtils.getNextNthInstallmentDueDate(i))
                    .amount(BigDecimal.valueOf(16666))
                    .loanId(1)
                    .isPaid(false)
                    .build());
        }

        when(customerQueryService.findCustomerByLoanId(anyInt())).thenReturn(customer);
        when(loanQueryService.findLoanById(anyInt())).thenReturn(loan);

        UserDetails admin = User.builder()
                .username("admin")
                .password("qwe1234")
                .roles("ADMIN")
                .build();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(admin);

        when(loanInstallmentsQueryService.getUnPaidLoanInstallmentsByLoanId(anyInt())).thenReturn(loanInstallments);

        //when
        PayLoanResponse response = loanCapabilityService.payLoan(payLoanRequest) ;

        //then
        verify(loanInstallmentUpdateService, times(1)).updateLoanInstallments(anyList());
        verify(customerUpdateService, times(1)).updateCustomer(any(Customer.class));
        assertNotNull(response);
    }

}


