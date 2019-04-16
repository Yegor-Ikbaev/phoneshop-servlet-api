package com.es.phoneshop.model.checkout;

public enum PaymentMethod {
    CARD("Card"), CASH("Cash");

    private String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
