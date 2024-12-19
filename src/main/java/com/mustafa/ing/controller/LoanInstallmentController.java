package com.mustafa.ing.controller;

import com.mustafa.ing.model.LoanInstallment;
import com.mustafa.ing.service.LoanInstallmentsQueryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = LoanInstallmentController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanInstallmentController {

    static final String ENDPOINT = "/loans-installments";

    private final LoanInstallmentsQueryService loanInstallmentsQueryService;

        @GetMapping(value = "/by-loan-id/{loanId}")
    public List<LoanInstallment> getLoanInstallment(@NotNull @PathVariable("loanId") int loanId) {
        return loanInstallmentsQueryService.findAllLoanInstallmentsByLoanId(loanId);
    }
}
