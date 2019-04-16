package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private String id;

    private List<CartItem> cartItems = new ArrayList<>();

    private ContactDetails contactDetails;

    private DeliveryDetails deliveryDetails;

    private PaymentMethod paymentMethod;

    private BigDecimal priceOfProducts = BigDecimal.ZERO;

    private BigDecimal totalPrice = BigDecimal.ZERO;

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