package ru.turaev.goods.exception;

import org.springframework.http.HttpStatus;

public class AccountingNotFoundException extends BaseException {
    public AccountingNotFoundException() {
        this("Accounting not found");
    }

    public AccountingNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}