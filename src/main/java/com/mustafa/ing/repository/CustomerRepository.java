package com.mustafa.ing.repository;

import com.mustafa.ing.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("""
                SELECT c FROM Customer c, Loan l
                WHERE c.id = l.customerId
                AND l.Id = :loanId
            """)
    Optional<Customer> findCustomerByLoanId(@Param("loanId") int loanId);
}
