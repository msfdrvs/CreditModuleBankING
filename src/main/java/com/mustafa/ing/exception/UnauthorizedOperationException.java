package com.mustafa.ing.exception;

public class UnauthorizedOperationException extends RuntimeException {
    public UnauthorizedOperationException() {
        super("Yeterli yetki bulunmamaktadır.");
    }
}
