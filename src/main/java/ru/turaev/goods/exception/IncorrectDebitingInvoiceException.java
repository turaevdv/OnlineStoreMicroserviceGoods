package ru.turaev.goods.exception;

import org.springframework.http.HttpStatus;

public class IncorrectDebitingInvoiceException extends BaseException {

    public IncorrectDebitingInvoiceException() {
        this("Debiting invoice is incorrect");
    }

    public IncorrectDebitingInvoiceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}