package com.mustafa.ing.service;

import com.mustafa.ing.dto.CreateLoanRequest;
import com.mustafa.ing.dto.CreateLoanResponse;
import com.mustafa.ing.mapper.LoanMapper;
import com.mustafa.ing.model.Customer;
import com.mustafa.ing.model.Loan;
import com.mustafa.ing.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanCreateServiceTest {

    @InjectMocks
    private LoanCreateService loanCreateService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private CustomerQueryService customerQueryService;

    @Mock
    private LoanInstallmentCreateService loanInstallmentCreateService;

    @Mock
    private CustomerUpdateService customerUpdateService;

    @Mock
    private LoanMapper loanMapper;

    @Test
    void shouldNotCreateLoan() {
        //given
        CreateLoanRequest request = CreateLoanRequest.builder()
                .amount(BigDecimal.valueOf(100000))
                .customerId(1)
                .numberOfInstallments(6)
                .interestRate(0.1f)
                .build();

        UserDetails applicationUser = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);

        //when
        assertThrows(RuntimeException.class, () -> loanCreateService.createLoan(request));

        //then
        verify(loanInstallmentCreateService, times(0)).createLoanInstallments(any(Loan.class));
        verify(customerUpdateService, times(0)).updateCustomer(any(Customer.class));
    }

    @Test
    void shouldCreateLoan() {
        //given
        CreateLoanRequest request = CreateLoanRequest.builder()
                .amount(BigDecimal.valueOf(100000))
                .customerId(1)
                .numberOfInstallments(6)
                .interestRate(0.1f)
                .build();

        Loan loan = Loan.builder()
                .id(1)
                .customerId(1)
                .loanAmount(BigDecimal.valueOf(100000))
                .numberOfInstallments(6)
                .isPaid(false)
                .createDate(new Date())
                .build();

        Customer customer = Customer.builder()
                .id(1)
                .username("ahmet")
                .usedCreditLimit(BigDecimal.ZERO)
                .creditLimit(BigDecimal.valueOf(100000))
                .build();

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

        when(customerQueryService.findCustomerById(anyInt())).thenReturn(customer);
        when(loanMapper.map(any(CreateLoanRequest.class))).thenReturn(loan);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanInstallmentCreateService.createLoanInstallments(any(Loan.class))).thenReturn(List.of());

        //when
        CreateLoanResponse response = loanCreateService.createLoan(request);

        //then
        verify(loanInstallmentCreateService, times(1)).createLoanInstallments(any(Loan.class));
        verify(customerUpdateService, times(1)).updateCustomer(any(Customer.class));
        assertNotNull(response);
    }




}