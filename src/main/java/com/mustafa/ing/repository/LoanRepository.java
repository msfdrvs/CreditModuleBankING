package com.mustafa.ing.repository;

import com.mustafa.ing.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findAllByCustomerIdOrderByCreateDateAsc(int loanId);
}
