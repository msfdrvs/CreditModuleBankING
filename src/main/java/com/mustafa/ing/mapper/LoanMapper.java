package com.mustafa.ing.mapper;

import com.mustafa.ing.dto.CreateLoanRequest;
import com.mustafa.ing.model.Loan;
import com.mustafa.ing.util.CalculationUtils;
import com.mustafa.ing.util.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source="createLoanRequest", target = "loanAmount", qualifiedByName = "calculateLoanTotalAmount")
    @Mapping(source = "numberOfInstallments", target = "numberOfInstallments")
    @Mapping(target="createDate", expression = "java(getCurrentDate())")
    @Mapping(target="isPaid", constant = "false")
    Loan map(CreateLoanRequest createLoanRequest);

    @Named("calculateLoanTotalAmount")
    static BigDecimal calculateLoanTotalAmount(CreateLoanRequest createLoanRequest) {
        return CalculationUtils.calculateTotalLoanAmount(createLoanRequest.getAmount(),
                createLoanRequest.getInterestRate());
    }

    default Date getCurrentDate() {
        return DateUtils.getCurrentDate();
    }

}
