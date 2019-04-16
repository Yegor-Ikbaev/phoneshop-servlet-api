package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;

import java.util.Arrays;
import java.util.List;

public interface OrderService {

    DeliveryMode getDeliveryMode(String description);

    List<DeliveryMode> getDeliveryModes();

    PaymentMethod getPaymentMethod(String description);

    List<PaymentMethod> getPaymentMethods();

    DeliveryDate getDeliveryDate(String description);

    List<DeliveryDate> getDeliveryDates();

    Order createOrder(Cart cart, ContactDetails contactDetails,
                      DeliveryDetails deliveryDetails, PaymentMethod paymentMethod);

    void placeOrder(Order order, Cart cart);

    void calculatePrice(Order order);
}
