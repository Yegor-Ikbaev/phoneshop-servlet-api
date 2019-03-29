package com.es.phoneshop.model.storage;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface CustomerMemoryService {

    Storage getStorageFromSource(Object source);

    void update(Storage storage, Product product);

    List<Product> getRecentlyViewedProducts(Storage storage);
}