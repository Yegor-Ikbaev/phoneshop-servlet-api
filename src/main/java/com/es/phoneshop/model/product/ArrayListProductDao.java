package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    		       .filter(product -> product.getId().equals(id))
    		       .findAny()
    		       .orElseThrow(() -> new IllegalArgumentException("There is no product with id = " + id));
    }

    @Override
    public synchronized List<Product> findProducts() {
	return products.parallelStream()
		       .filter(product -> product.getPrice() != null && product.getStock() > 0)
		       .collect(Collectors.toList());
    }
    
    @Override
    public synchronized List<Product> findProductsByDescription(String query) {
	String[] keyWords = query.split("\\s");
	Map<Product, Integer> mapOfMatches = new HashMap<>();
	products.parallelStream()
		.forEach(product -> {
		    int numberOfMatches = numberOfMatches(product, keyWords);
		    if (numberOfMatches > 0 && product.getPrice() != null && product.getStock() > 0) {
			mapOfMatches.put(product, numberOfMatches);
		    }
		});
	return mapOfMatches.keySet().parallelStream()
		                    .sorted((p1, p2) -> mapOfMatches.get(p2) - mapOfMatches.get(p1))
		                    .collect(Collectors.toList());
    }
    
    private int numberOfMatches(Product product, String... keyWords) {
	int result = 0;
	for (String keyWord : keyWords) {
	    Pattern pattern = Pattern.compile(keyWord, Pattern.CASE_INSENSITIVE);
	    if (pattern.matcher(product.getDescription()).find()) {
		result++;
	    }
	}
	return result;
    }

    @Override
    public synchronized void save(Product product) throws IllegalArgumentException {
	if(product == null || product.getId() == null) {
	    throw new IllegalArgumentException("Product or product id can not be null");
	}
	
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