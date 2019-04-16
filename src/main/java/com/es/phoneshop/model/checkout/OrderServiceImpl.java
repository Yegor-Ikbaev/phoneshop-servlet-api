package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static final OrderService INSTANCE = new OrderServiceImpl();

    public static OrderService getInstance() {
        return INSTANCE;
    }

    private OrderServiceImpl() {
    }

    @Override
    public Order createOrder(Cart cart, ContactDetails contactDetails,
                             DeliveryDetails deliveryDetails, PaymentMethod paymentMethod) {
        Order order = new Order();
        order.setContactDetails(contactDetails);
        order.setDeliveryDetails(deliveryDetails);
        order.setPaymentMethod(paymentMethod);
        order.setPriceOfProducts(cart.getTotalPrice());
        List<CartItem> cartItems = cart.getCartItems().stream()
                .map(CartItem::new)
                .collect(Collectors.toList());
        order.setCartItems(cartItems);
        return order;
    }

    @Override
    public void placeOrder(Order order, Cart cart) {
        order.setId(generateUniqueId());
        calculatePrice(order);
        ArrayListOrderDao.getInstance().save(order);
        HttpSessionCartService.getInstance().clear(cart);
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void calculatePrice(Order order) {
        BigDecimal totalPrice = order.getPriceOfProducts().add(order.getDeliveryDetails().getPrice());
        order.setTotalPrice(totalPrice);
    }

    @Override
    public DeliveryMode getDeliveryMode(String description) {
        return getDeliveryModes().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    @Override
    public PaymentMethod getPaymentMethod(String description) {
        return getPaymentMethods().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public DeliveryDate getDeliveryDate(String description) {
        return getDeliveryDates().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<DeliveryDate> getDeliveryDates() {
        return Arrays.asList(DeliveryDate.values());
    }
}