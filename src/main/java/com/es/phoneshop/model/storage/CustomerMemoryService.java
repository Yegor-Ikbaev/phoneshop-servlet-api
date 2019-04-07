package com.es.phoneshop.model.storage;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CustomerMemoryService {

    Storage getStorage(HttpServletRequest request);

    void update(Storage storage, Product product);

    List<Product> getRecentlyViewedProducts(Storage storage);
}