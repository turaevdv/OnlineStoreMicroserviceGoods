package ru.turaev.goods.exception;

import org.springframework.http.HttpStatus;

public class IncorrectOrderException extends BaseException {
    public IncorrectOrderException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
