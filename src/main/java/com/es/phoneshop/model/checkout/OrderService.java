package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;

import java.util.Arrays;
import java.util.List;

public interface OrderService {

    default DeliveryMode getDeliveryMode(String description) {
        return getDeliveryModes().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    default List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    default PaymentMethod getPaymentMethod(String description) {
        return getPaymentMethods().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    default List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    default DeliveryDate getDeliveryDate(String description) {
        return getDeliveryDates().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    default List<DeliveryDate> getDeliveryDates() {
        return Arrays.asList(DeliveryDate.values());
    }

    Order createOrder(Cart cart);

    void placeOrder(Order order, ContactDetails contactDetails,
                    DeliveryDetails deliveryDetails, PaymentMethod paymentMethod);

    void calculatePrice(Order order);
}
