package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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
    		       .orElseThrow(() -> new IllegalArgumentException("There is no product with id = " + id));
    }

    @Override
    public synchronized List<Product> findProducts() {
	return products.parallelStream()
		       .filter((product) -> product.getPrice() != null && product.getStock() > 0)
		       .collect(Collectors.toList());
    }
    
    @Override
    public synchronized List<Product> findProductsByDescription(String query) {
	if (query != null && !query.isEmpty()) {
	    String[] keyWords = query.split("\\s");
	    return products.parallelStream()
		           .filter((product) -> numberOfMatches(product, keyWords) > 0)
		           .filter((product) -> product.getPrice() != null && product.getStock() > 0)
		           .sorted((p1, p2) -> numberOfMatches(p2, keyWords) - numberOfMatches(p1, keyWords))
		           .collect(Collectors.toList());
	} else {
	    return findProducts();
	}
    }
    
    private int numberOfMatches(Product product, String... keyWords) {
	int result = 0;
	for (String keyWord : keyWords) {
	    Pattern pattern = Pattern.compile("\\b" + keyWord + "\\b", Pattern.CASE_INSENSITIVE);
	    if (pattern.matcher(product.getDescription()).find()) {
		result++;
	    }
	}
	return result;
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