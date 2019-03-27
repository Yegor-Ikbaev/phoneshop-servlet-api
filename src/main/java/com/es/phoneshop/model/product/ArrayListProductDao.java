package com.es.phoneshop.model.product;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static final ProductDao INSTANCE = new ArrayListProductDao();

    private List<Product> products;

    private Map<String, Comparator<Product>> comparators;

    private ArrayListProductDao() {
        products = new ArrayList<>();
        comparators = new HashMap<>();
        comparators.put("price", Comparator.comparing(Product::getPrice));
        comparators.put("description", Comparator.comparing(Product::getDescription));
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized Product getProduct(Long id) {
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
    public synchronized void save(Product product) {
        if (product == null || product.getId() == null) {
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
    public synchronized void delete(Long id) {
        products.remove(getProduct(id));
    }

    @Override
    public synchronized List<Product> findProductsByDescription(String query) {
        List<String> keyWords = Arrays.asList(query.split("\\s"));
        List<Product> validProducts = findProducts();

        Map<Product, Integer> mapOfMatches = validProducts.stream()
                .collect(Collectors.toMap(Function.identity(), product -> numberOfMatches(product, keyWords)));

        return mapOfMatches.keySet().stream()
                .filter(product -> mapOfMatches.get(product) > 0)
                .sorted(Comparator.comparingInt(mapOfMatches::get).reversed())
                .collect(Collectors.toList());
    }

    private int numberOfMatches(Product product, List<String> keyWords) {
        return (int) keyWords.stream()
                .filter(product.getDescription()::contains)
                .count();
    }

    @Override
    public synchronized List<Product> sort(List<Product> unsortedProducts, String productField, String order) {
        if (productField != null && !productField.isEmpty()) {
            return unsortedProducts.stream()
                    .sorted(getComparator(productField, order))
                    .collect(Collectors.toList());
        } else {
            return unsortedProducts;
        }
    }

    private Comparator<Product> getComparator(String productField, String order) {
        if (!comparators.containsKey(productField)) {
            throw new IllegalArgumentException("There is no comparator for sorting by " + productField);
        }
        Comparator<Product> comparator = comparators.get(productField);
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}