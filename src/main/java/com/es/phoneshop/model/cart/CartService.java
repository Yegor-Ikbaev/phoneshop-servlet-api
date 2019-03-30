package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public interface CartService {

    Cart getCart(HttpSession httpSession);

    void add(Cart cart, Product product, int quantity) throws LackOfStockException;
}