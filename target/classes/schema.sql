create table Customer(
    id int auto_increment PRIMARY KEY,
    username varchar(50) UNIQUE,
    name varchar(255) NOT NULL,
    surname varchar(255) NOT NULL,
    credit_limit DECIMAL(20, 2),
    used_credit_limit DECIMAL(20, 2)
);

create table Loan(
    id int auto_increment PRIMARY KEY,
    customer_id int NOT NULL,
    loan_amount DECIMAL(20, 2) NOT NULL,
    number_of_installments INT NOT NULL,
    create_date DATE NOT NULL,
    is_paid BOOLEAN
);

create table Loan_Installment(
    id int auto_increment PRIMARY KEY,
    loan_id int NOT NULL,
    amount DECIMAL(20, 2) NOT NULL,
    paid_amount DECIMAL(20, 2),
    due_date DATE NOT NULL,
    payment_date DATE,
    is_paid BOOLEAN
);