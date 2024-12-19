package com.mustafa.ing.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super("Kayıt Bulunamadı.");
    }
}
