package com.es.phoneshop.model.storage;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class HttpSessionCustomerMemory implements CustomerMemoryService {

    private static final String STORAGE_ATTRIBUTE = "storage";

    private static final CustomerMemoryService INSTANCE = new HttpSessionCustomerMemory();

    private HttpSessionCustomerMemory() {
    }

    public static CustomerMemoryService getInstance() {
        return INSTANCE;
    }

    @Override
    public Storage getStorage(HttpSession httpSession) {
        Storage storage = (Storage) httpSession.getAttribute(STORAGE_ATTRIBUTE);
        if (storage == null) {
            storage = new Storage();
            httpSession.setAttribute(STORAGE_ATTRIBUTE, storage);
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
                .limit(3)
                .collect(Collectors.toList());
    }
}
