package com.mustafa.ing.mapper;

import com.mustafa.ing.dto.CreateLoanRequest;
import com.mustafa.ing.model.Loan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-19T08:52:15+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public Loan map(CreateLoanRequest createLoanRequest) {
        if ( createLoanRequest == null ) {
            return null;
        }

        Loan.LoanBuilder loan = Loan.builder();

        loan.loanAmount( LoanMapper.calculateLoanTotalAmount( createLoanRequest ) );
        loan.numberOfInstallments( createLoanRequest.getNumberOfInstallments() );
        loan.customerId( createLoanRequest.getCustomerId() );

        loan.createDate( getCurrentDate() );
        loan.isPaid( false );

        return loan.build();
    }
}
