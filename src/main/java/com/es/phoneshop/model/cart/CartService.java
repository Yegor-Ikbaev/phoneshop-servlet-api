package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.Product;

public interface CartService {

    Cart getCartFromSource(Object source);

    void add(Cart cart, Product product, int quantity) throws LackOfStockException;
}