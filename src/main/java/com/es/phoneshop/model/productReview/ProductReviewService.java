package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductReviewService {

    List<ProductReview> getProductReviews(Product product);

    void approve(ProductReview productReview);

    void placeReview(ProductReview productReview);

    ProductReview create(Product product, String userName, String text, int rating);
}