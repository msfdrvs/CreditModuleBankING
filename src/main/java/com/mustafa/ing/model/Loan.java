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
@Table(name="Loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="customer_id")
    private Integer customerId;

    @Column(name="loan_amount")
    private BigDecimal loanAmount;

    @Column(name="number_of_installments")
    private int numberOfInstallments;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="is_paid")
    private Boolean isPaid;
}
