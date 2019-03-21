package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
	productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setAttribute("products", getFilteredProducts(request));
	request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<Product> getFilteredProducts(HttpServletRequest request) {
	String searchParameter = request.getParameter("search");
	String sortParameter = request.getParameter("sortBy");
	String order = request.getParameter("order");
	List<Product> products = productDao.findProductsByDescription(searchParameter);
	if ("description".equals(sortParameter)) {
	    products.sort(Comparator.comparing(Product::getDescription));
	}
	if ("price".equals(sortParameter)) {
	    products.sort(Comparator.comparing(Product::getPrice));
	}
	if ("desc".equals(order)) {
	    Collections.reverse(products);
	}
	return products;
    }
}