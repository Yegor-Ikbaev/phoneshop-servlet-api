package com.es.phoneshop.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
	productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setAttribute("product", productDao.getProduct(extractId(request)));
	request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }
    
    private Long extractId(HttpServletRequest request) {
	return Long.parseLong(request.getPathInfo().replaceAll("/", ""));
    }
}