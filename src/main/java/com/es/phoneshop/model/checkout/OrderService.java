package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;

public interface OrderService {

    Order createOrder(Cart cart);

    void placeOrder(Order order, ContactDetails contactDetails,
                    DeliveryDetails deliveryDetails, PaymentMethod paymentMethod);

    void calculatePrice(Order order);
}
