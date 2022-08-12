package ru.turaev.goods.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {

    public ProductNotFoundException() {
        this("Product not found");
    }

    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
