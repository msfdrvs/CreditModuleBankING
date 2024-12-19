package com.mustafa.ing.repository;

import com.mustafa.ing.model.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Integer> {

    List<LoanInstallment> findAllByLoanId(int loanId);

    List<LoanInstallment> findAllByLoanIdAndIsPaidIsFalseOrderByDueDateAsc(int loanId);
}
