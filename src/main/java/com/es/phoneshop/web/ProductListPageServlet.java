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
import java.util.stream.Collectors;

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
	List<Product> products;
	if (searchParameter != null && !searchParameter.isEmpty()) {
	    products = productDao.findProductsByDescription(searchParameter);
	} else {
	    products = productDao.findProducts();
	}
	if ("description".equalsIgnoreCase(sortParameter)) {
	    products = products.parallelStream()
	                       .sorted(Comparator.comparing(Product::getDescription))
	                       .collect(Collectors.toList());
	}
	if ("price".equalsIgnoreCase(sortParameter)) {
	    products = products.parallelStream()
                               .sorted(Comparator.comparing(Product::getPrice))
                               .collect(Collectors.toList());
	}
	if ("desc".equalsIgnoreCase(order)) {
	    Collections.reverse(products);
	}
	return products;
    }
}