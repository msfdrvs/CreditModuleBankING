# CreditModuleBankING
Here you can find some fundamental information abour this service project. Basicly project provide APIs to handle some loan operation like craeteLoan, findLoan, findLoanInstallments or payLoan.

## Dependencies:
* Spring Security
* Spring Web
* H2 Database
* Lombok
* Mockito
* Mapstruct

## Users And Roles:
Project use Spring Securty for authentication and authorizations. Some users are added as predifiend user from SecurityConfiguration. User may have two main role that are admin and customer. Admin user can use all APIs with any customer information. On the other hand users that have only customer role can use APIs with their own credential and only reach and manipulate thier own data.
There is also a scheme.sql file to create scheme on h2 database. New customers can be inserted by using this file.

## APIs and functinalities:

### Create Loan `POST/loans`
This request used to define new loan for specific customer. Each customer has their own credit limit. Customer can only create new loan with in that limit. Used credit limit can regain by paying loan installments. Number of installments can only be selected from 6, 9, 12, 24 and also interest rate can be between only 0.1 – 0.5.
#### cURL
 curl --location 'localhost:8080/loans' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": 1,
    "amount": 100000,
    "interestRate": 0.1,
    "numberOfInstallments": 6
}'

### Find Loan `GET/loans/get-loan-by-customer/{customerId}`
This request used to find craeted loan by customer information.
#### cURL
curl --location 'localhost:8080/loans/get-loan-by-customer/1' \

### Find Loan Intallments `GET/loans-installments/by-loan-id/{loanId}`
This request used to list craeted loan installments by customer information.
#### cURL
curl --location 'localhost:8080/loans-installments/by-loan-id/1' \

### Find Loan Intallments `POST/loan-capability/pay-loan`
This request used to pay defined loan installments. Multiple installments can be paid at the same time but an installment can not be paid partially. Each installment has its own due date that are sequencal and scheduled asfirst day of months. Payment done before due dates make some discount from installment amount according to day count. On contrary payment done after due date couse some penalty that increase installment amount. An finally an installment can not be paid if it have due date that still more than three calendar months.
#### cURL
curl --location 'localhost:8080/loan-capability/pay-loan' \
--header 'Content-Type: application/json' \
--data '{
    "loanId": 1,
    "amount": 40000
}'