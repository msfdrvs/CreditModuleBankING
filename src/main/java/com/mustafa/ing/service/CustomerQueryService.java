package com.mustafa.ing.service;

import com.mustafa.ing.exception.RecordNotFoundException;
import com.mustafa.ing.model.Customer;
import com.mustafa.ing.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerQueryService {

    private final CustomerRepository customerRepository;

    public Customer findCustomerById(int customerId) throws RecordNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(RecordNotFoundException::new);
    }

    public Customer findCustomerByLoanId(int loanId) {
        return customerRepository.findCustomerByLoanId(loanId).orElseThrow(RecordNotFoundException::new);
    }
}
