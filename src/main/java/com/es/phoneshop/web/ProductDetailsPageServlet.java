package com.es.phoneshop.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;

    private CartService cartService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("product", productDao.getProduct(extractId(request)));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String quantityParameter = "&quantity=" + request.getParameter("quantity");
        int quantity;
        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 1) {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException e) {
            String exceptionParameter = "?exception=Quantity should be a number";
            response.sendRedirect(request.getRequestURI() + exceptionParameter + quantityParameter);
            return;
        } catch (IllegalArgumentException e) {
            String exceptionParameter = "?exception=Quantity should be greater than 0";
            response.sendRedirect(request.getRequestURI() + exceptionParameter + quantityParameter);
            return;
        }
        try {
            updateCart(request, quantity);
        } catch (LackOfStockException e) {
            String exceptionParameter = "?exception=" + e.getMessage();
            response.sendRedirect(request.getRequestURI() + exceptionParameter + quantityParameter);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?success=CartItem added successfully" + quantityParameter);
    }

    private void updateCart(HttpServletRequest request, int quantity) throws LackOfStockException {
        Product product = productDao.getProduct(extractId(request));
        Cart cart = cartService.getCartFromSource(request.getSession());
        cartService.add(cart, product, quantity);
    }

    private Long extractId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().replaceAll("/", ""));
    }
}