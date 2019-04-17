package com.es.phoneshop.web;

import com.es.phoneshop.model.productReview.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApproveProductReviewServlet extends HttpServlet {

    private ProductReviewDao productReviewDao;

    private ProductReviewService service;

    @Override
    public void init() {
        productReviewDao = ArrayListProductReviewDao.getInstance();
        service = ProductReviewServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("reviewes", productReviewDao.getProductReviews());
        request.getRequestDispatcher("/WEB-INF/pages/productReviewes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ProductReview productReview = productReviewDao.getProductReview(extractId(request));
        service.approve(productReview);
        response.sendRedirect(request.getRequestURI());
    }

    private String extractId(HttpServletRequest request) {
        return request.getPathInfo().replaceAll("/", "");
    }
}