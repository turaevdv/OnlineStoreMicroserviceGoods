package ru.turaev.goods.exception;

import org.springframework.http.HttpStatus;

public class IncorrectProductException extends BaseException {

    public IncorrectProductException() {
        this("Product is incorrect");
    }

    public IncorrectProductException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}