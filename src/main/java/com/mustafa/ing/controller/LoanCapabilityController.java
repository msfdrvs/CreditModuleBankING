package com.mustafa.ing.controller;

import com.mustafa.ing.dto.PayLoanRequest;
import com.mustafa.ing.dto.PayLoanResponse;
import com.mustafa.ing.service.LoanCapabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = LoanCapabilityController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanCapabilityController {

    static final String ENDPOINT = "/loan-capability";

    private final LoanCapabilityService loanCapabilityService;

    @PostMapping(value = "/pay-loan", produces = MediaType.APPLICATION_JSON_VALUE)
    public PayLoanResponse createLoad(@Valid @RequestBody PayLoanRequest request) {
        return loanCapabilityService.payLoan(request);
    }



}
