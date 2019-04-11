package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

    private String id;

    private List<CartItem> cartItems;

    private ContactDetails contactDetails;

    private DeliveryDetails deliveryDetails;

    private PaymentMethod paymentMethod;

    private BigDecimal priceOfProducts;

    private BigDecimal totalPrice;

    public Order(Cart cart) {
        cartItems = cart.getCartItems().stream()
                .map(CartItem::new)
                .collect(Collectors.toList());
        priceOfProducts = cart.getTotalPrice();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public DeliveryDetails getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getPriceOfProducts() {
        return priceOfProducts;
    }

    public void setPriceOfProducts(BigDecimal priceOfProducts) {
        this.priceOfProducts = priceOfProducts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}