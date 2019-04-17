package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.productReview.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductReviewServlet  extends HttpServlet {

    private ProductDao productDao;

    private ProductReviewDao productReviewDao;

    private ProductReviewService service;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        productReviewDao = ArrayListProductReviewDao.getInstance();
        service = ProductReviewServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String userName = request.getParameter("username");
            if (userName == null || userName.isEmpty()) {
                throw new IllegalArgumentException("Username is incorrect");
            }
            String text = request.getParameter("text");
            int rating = Integer.parseInt(request.getParameter("rating"));
            Product product = productDao.getProduct(extractId(request));
            ProductReview productReview = service.create(product, userName, text, rating);
            service.placeReview(productReview);
        } catch (NumberFormatException e){
            sendError("Rating should be a number", request, response);
            return;
        } catch (IllegalArgumentException e){
            sendError(e.getMessage(), request, response);
            return;
        }
        response.sendRedirect( request.getContextPath() + "/products/" + extractId(request) + "?successReview=true");
    }

    private void sendError(String message, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String url = request.getContextPath() + "/products/" + extractId(request) + "?reviewErrorMessage=" + message;
        response.sendRedirect(url );
    }

    private Long extractId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().replaceAll("/", ""));
    }
}

