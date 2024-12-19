package com.mustafa.ing.service;


import com.mustafa.ing.model.Customer;
import com.mustafa.ing.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerUpdateServiceTest {

    @InjectMocks
    private CustomerUpdateService customerUpdateService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldUpdateCustomer() {
        //given
        Customer customer = Customer.builder()
                .id(1)
                .name("Mehmet")
                .surname("Deneme")
                .build();

        //when
        customerUpdateService.updateCustomer(customer);

        //then
        verify(customerRepository, times(1)).save(any(Customer.class));

    }



}