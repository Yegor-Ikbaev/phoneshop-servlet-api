package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("products", getFilteredProducts(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<Product> getFilteredProducts(HttpServletRequest request) {
        String searchValue = request.getParameter("search");
        List<Product> products;
        if (searchValue != null && !searchValue.isEmpty()) {
            products = productDao.findProductsByDescription(searchValue);
        } else {
            products = productDao.findProducts();
        }

        String sortParameter = request.getParameter("sortBy");
        if (sortParameter != null) {
            products = productDao.sort(products, sortParameter, request.getParameter("order"));
        }
        return products;
    }
}