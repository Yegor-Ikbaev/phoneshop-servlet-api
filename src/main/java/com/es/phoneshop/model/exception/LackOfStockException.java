package com.es.phoneshop.model.exception;

public class LackOfStockException extends Exception {
    public LackOfStockException(String message) {
        super(message);
    }
}