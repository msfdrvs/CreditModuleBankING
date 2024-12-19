package com.mustafa.ing.controller;

import com.mustafa.ing.dto.CreateLoanRequest;
import com.mustafa.ing.dto.CreateLoanResponse;
import com.mustafa.ing.model.Loan;
import com.mustafa.ing.service.LoanCreateService;
import com.mustafa.ing.service.LoanQueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = LoanController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanController {

    static final String ENDPOINT = "/loans";

    private final LoanCreateService loanCreateService;

    private final LoanQueryService loanQueryService;

    @PostMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateLoanResponse createLoad(@Valid @RequestBody CreateLoanRequest request) {
        return loanCreateService.createLoan(request);
    }

    @GetMapping(value = "/get-loan-by-customer/{customerId}")
    public List<Loan> getLoan(@NotNull @PathVariable("customerId") int customerId) {
        return loanQueryService.findLoanByCustomerId(customerId);
    }



}
