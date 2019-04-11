package com.es.phoneshop.model.checkout;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public enum DeliveryMode {
    STORE_PICKUP(BigDecimal.ZERO, "Store pickup"), COURIER(BigDecimal.TEN, "Courier");

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    private String description;

    DeliveryMode(BigDecimal price, String description) {
        this.price = price;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static DeliveryMode getDeliveryMode(String description) {
        return getDeliveryModes().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(values());
    }
}
