package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductReviewDao {

    ProductReview getProductReview(String id);

    void save(ProductReview productReview);

    List<ProductReview> getProductReviews(Product product);

    List<ProductReview> getProductReviews();
}
