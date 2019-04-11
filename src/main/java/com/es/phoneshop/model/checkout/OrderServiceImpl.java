package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    private static final OrderService INSTANCE = new OrderServiceImpl();

    public static OrderService getInstance() {
        return INSTANCE;
    }

    private OrderServiceImpl() {
    }

    @Override
    public Order createOrder(Cart cart) {
        return new Order(cart);
    }

    @Override
    public void placeOrder(Order order, ContactDetails contactDetails,
                           DeliveryDetails deliveryDetails, PaymentMethod paymentMethod) {
        order.setId(generateUniqueId());
        order.setContactDetails(contactDetails);
        order.setDeliveryDetails(deliveryDetails);
        order.setPaymentMethod(paymentMethod);
        calculatePrice(order);
        ArrayListOrderDao.getInstance().save(order);
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void calculatePrice(Order order) {
        BigDecimal totalPrice = order.getPriceOfProducts().add(order.getDeliveryDetails().getPrice());
        order.setTotalPrice(totalPrice);
    }
}