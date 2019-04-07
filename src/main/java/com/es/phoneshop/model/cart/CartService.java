package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);

    void add(Cart cart, Product product, int quantity) throws LackOfStockException, IllegalQuantityException;

    void update(Cart cart, Product product, int quantity) throws LackOfStockException, IllegalQuantityException;

    void delete(Cart cart, Product product);

    void calculateTotalPrice(Cart cart);
}