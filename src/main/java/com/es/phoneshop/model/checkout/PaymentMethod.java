package com.es.phoneshop.model.checkout;

import java.util.Arrays;
import java.util.List;

public enum PaymentMethod {
    CARD("Card"), CASH("Cash");

    private String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentMethod getPaymentMethod(String description) {
        return getPaymentMethods().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(values());
    }
}
