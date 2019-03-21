package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static final ProductDao INSTANCE = new ArrayListProductDao();

    private List<Product> products;

    private ArrayListProductDao() {
	products = new ArrayList<>();
    }

    public static ProductDao getInstance() {
	return INSTANCE;
    }

    @Override
    public synchronized Product getProduct(Long id) throws IllegalArgumentException {
	return products.parallelStream()
    		       .filter((product) -> product.getId().equals(id))
    		       .findAny()
    		       .orElseThrow(() -> new IllegalArgumentException("There is no product with such id"));
    }

    @Override
    public synchronized List<Product> findProducts() {
	return products.parallelStream()
		       .filter((product) -> product.getPrice() != null && product.getStock() > 0)
		       .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) throws IllegalArgumentException {
	Product productWithSameId;
	try {
	    productWithSameId = getProduct(product.getId());
	} catch (IllegalArgumentException exception) {
	    productWithSameId = null;
	}

	if (productWithSameId == null) {
	    products.add(product);
	} else {
	    throw new IllegalArgumentException("Product with such id is already exists");
	}
    }

    @Override
    public synchronized void delete(Long id) throws IllegalArgumentException {
	products.remove(getProduct(id));
    }
}