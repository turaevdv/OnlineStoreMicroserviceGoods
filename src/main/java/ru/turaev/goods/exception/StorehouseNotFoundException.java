package ru.turaev.goods.exception;

import org.springframework.http.HttpStatus;

public class StorehouseNotFoundException extends BaseException {
    public StorehouseNotFoundException() {
        this("Storehouse not found");
    }

    public StorehouseNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
