package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductReviewDao implements ProductReviewDao {

    private List<ProductReview> productReviews = new ArrayList<>();

    private static final ProductReviewDao INSTANCE = new ArrayListProductReviewDao();

    public static ProductReviewDao getInstance() {
        return INSTANCE;
    }

    @Override
    public ProductReview getProductReview(String id) {
        return productReviews.stream()
                .filter(productReview -> productReview.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("There is no review with id = " + id));
    }

    @Override
    public void save(ProductReview productReview) {
        productReviews.add(productReview);
    }

    @Override
    public List<ProductReview> getApprovedProductReviews(Product product) {
        return productReviews.stream()
                .filter(productReview -> productReview.getProduct().equals(product))
                .filter(ProductReview::isApproved)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReview> getUnapprovedProductReviews() {
         return productReviews.stream()
                .filter(productReview -> !productReview.isApproved())
                .collect(Collectors.toList());
    }
}
