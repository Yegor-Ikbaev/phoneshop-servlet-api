package com.es.phoneshop.model.exception;

public class OrderNotFoundException extends RuntimeException {
    public  OrderNotFoundException(String message) {
        super(message);
    }
}
