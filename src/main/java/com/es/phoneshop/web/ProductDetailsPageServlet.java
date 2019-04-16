package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.MiniCart;
import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.storage.CustomerMemoryService;
import com.es.phoneshop.model.storage.HttpSessionCustomerMemory;
import com.es.phoneshop.model.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private static final String QUANTITY_PARAMETER = "quantity";

    private ProductDao productDao;

    private CartService cartService;

    private CustomerMemoryService customerMemoryService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        customerMemoryService = HttpSessionCustomerMemory.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Product product = productDao.getProduct(extractId(request));
        Storage storage = customerMemoryService.getStorage(request);
        request.setAttribute("product", product);
        request.setAttribute("recentlyViewedProducts", customerMemoryService.getRecentlyViewedProducts(storage));
        customerMemoryService.update(storage, product);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int quantity;
        try {
            quantity = Integer.parseInt(request.getParameter(QUANTITY_PARAMETER));
        } catch (NumberFormatException e) {
            sendError("Quantity should be a number", request, response);
            return;
        }
        try {
            updateCart(request, quantity);
        } catch (IllegalQuantityException | LackOfStockException e) {
            sendError(e.getMessage(), request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?success=true");
    }

    private void sendError(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.setAttribute(QUANTITY_PARAMETER, request.getParameter(QUANTITY_PARAMETER));
        request.setAttribute("miniCart", new MiniCart(cartService.getCart(request)));
        doGet(request, response);
    }

    private void updateCart(HttpServletRequest request, int quantity)
            throws LackOfStockException, IllegalQuantityException {
        Product product = productDao.getProduct(extractId(request));
        Cart cart = cartService.getCart(request);
        cartService.add(cart, product, quantity);
        cartService.calculateTotalPrice(cart);
    }

    private Long extractId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().replaceAll("/", ""));
    }
}