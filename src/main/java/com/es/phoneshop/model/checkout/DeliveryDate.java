package com.es.phoneshop.model.checkout;

import java.util.Arrays;
import java.util.List;

public enum DeliveryDate {
    TODAY("Today"), TOMORROW("Tomorrow");

    private String description;

    DeliveryDate(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static DeliveryDate getDeliveryDate(String description) {
        return getDeliveryDates().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<DeliveryDate> getDeliveryDates() {
        return Arrays.asList(values());
    }
}
