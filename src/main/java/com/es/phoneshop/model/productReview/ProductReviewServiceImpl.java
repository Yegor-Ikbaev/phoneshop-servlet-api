package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.product.Product;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductReviewServiceImpl implements ProductReviewService {

    private static final ProductReviewService INSTANCE = new ProductReviewServiceImpl();

    public static ProductReviewService getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ProductReview> getProductReviews(Product product) {
        return ArrayListProductReviewDao.getInstance().getApprovedProductReviews(product).stream()
                .filter(ProductReview::isApproved)
                .collect(Collectors.toList());
    }

    @Override
    public void approve(ProductReview productReview) {
        productReview.setApproved(true);
    }

    @Override
    public void placeReview(ProductReview productReview) {
        ArrayListProductReviewDao.getInstance().save(productReview);
    }

    @Override
    public ProductReview create(Product product, String userName, String text, int rating) {
        ProductReview productReview = new ProductReview();
        if (rating > 0 && rating <=5) {
            productReview.setRating(rating);
            productReview.setProduct(product);
            productReview.setText(text);
            productReview.setUserName(userName);
            productReview.setId(generateUniqueId());
        } else {
            throw new IllegalArgumentException("Rating should be greater 0 and less or equals 5");
        }
        return productReview;
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
