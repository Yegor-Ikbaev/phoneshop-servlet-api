package com.es.phoneshop.model.storage;

import com.es.phoneshop.model.product.Product;

import java.util.Deque;
import java.util.LinkedList;

public class Storage {

    private Deque<Product> viewedProducts;

    public Storage() {
        viewedProducts = new LinkedList<>();
    }

    public Deque<Product> getViewedProducts() {
        return viewedProducts;
    }
}