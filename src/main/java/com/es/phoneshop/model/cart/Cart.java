package com.es.phoneshop.model.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}