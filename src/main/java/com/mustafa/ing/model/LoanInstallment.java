package com.mustafa.ing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name="Loan_Installment")
public class LoanInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="loan_id")
    private Integer loanId;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="paid_amount")
    private BigDecimal paidAmount;

    @Column(name="due_date")
    private Date dueDate;

    @Column(name="payment_date")
    private Date paymentDate;

    @Column(name="is_paid")
    private Boolean isPaid;
}
