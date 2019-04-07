package com.es.phoneshop.model.storage;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class HttpSessionCustomerMemory implements CustomerMemoryService {

    private static final String STORAGE_ATTRIBUTE = "storage";

    private static final int RECENTLY_VIEWED_PRODUCTS_NUMBER = 3;

    private static final CustomerMemoryService INSTANCE = new HttpSessionCustomerMemory();

    private HttpSessionCustomerMemory() {
    }

    public static CustomerMemoryService getInstance() {
        return INSTANCE;
    }

    @Override
    public Storage getStorage(HttpServletRequest request) {
        Storage storage = (Storage) request.getSession().getAttribute(STORAGE_ATTRIBUTE);
        if (storage == null) {
            storage = new Storage();
            request.getSession().setAttribute(STORAGE_ATTRIBUTE, storage);
        }
        return storage;
    }

    @Override
    public void update(Storage storage, Product product) {
        Deque<Product> products = storage.getViewedProducts();
        products.removeIf(product::equals);
        products.addFirst(product);
    }

    @Override
    public List<Product> getRecentlyViewedProducts(Storage storage) {
        return storage.getViewedProducts().stream()
                .limit(RECENTLY_VIEWED_PRODUCTS_NUMBER)
                .collect(Collectors.toList());
    }
}
