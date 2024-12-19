package com.mustafa.ing.service;

import com.mustafa.ing.model.Customer;
import com.mustafa.ing.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUpdateService {

    private final CustomerRepository customerRepository;

    public void updateCustomer(Customer customer) {
       customerRepository.save(customer);
    }
}
