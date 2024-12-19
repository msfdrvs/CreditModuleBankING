package com.mustafa.ing.service;

import com.mustafa.ing.exception.RecordNotFoundException;
import com.mustafa.ing.model.Customer;
import com.mustafa.ing.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerQueryServiceTest {

    @InjectMocks
    private CustomerQueryService customerQueryService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldFindCustomerById() {
        //given
        int customerId = 1;
        Customer customer = Customer.builder()
                .id(customerId)
                .build();

        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));

        //when
        Customer result =  customerQueryService.findCustomerById(customerId);

        //then
        verify(customerRepository, times(1)).findById(anyInt());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindCustomerById() {
        //given
        int customerId = 1;

        when(customerRepository.findById(anyInt())).thenThrow(new RecordNotFoundException());

        //when
        assertThrows(RecordNotFoundException.class, () -> customerQueryService.findCustomerById(customerId));

        //then
        verify(customerRepository, times(1)).findById(anyInt());
    }

    @Test
    void shouldFindCustomerByLoanId() {
        //given
        int loanId = 1;
        Customer customer = Customer.builder()
                .id(1)
                .build();

        when(customerRepository.findCustomerByLoanId(anyInt())).thenReturn(Optional.of(customer));

        //when
        Customer result =  customerQueryService.findCustomerByLoanId(loanId);

        //then
        verify(customerRepository, times(1)).findCustomerByLoanId(anyInt());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindCustomerByLoanId() {
        //given
        int loanId = 1;

        when(customerRepository.findCustomerByLoanId(anyInt())).thenThrow(new RecordNotFoundException());

        //when
        assertThrows(RecordNotFoundException.class, () -> customerQueryService.findCustomerByLoanId(loanId));

        //then
        verify(customerRepository, times(1)).findCustomerByLoanId(anyInt());
    }


}