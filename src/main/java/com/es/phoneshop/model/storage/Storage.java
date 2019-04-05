package com.es.phoneshop.model.storage;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

public class Storage implements Serializable {

    private Deque<Product> viewedProducts = new LinkedList<>();

    public Deque<Product> getViewedProducts() {
        return viewedProducts;
    }
}