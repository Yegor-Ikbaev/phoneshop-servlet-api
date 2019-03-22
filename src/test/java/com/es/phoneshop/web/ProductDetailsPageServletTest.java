package com.es.phoneshop.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ProductDetailsPageServlet servlet;

    private ProductDao productDao;

    @Before
    public void setup() {
        servlet = new ProductDetailsPageServlet();
        productDao = ArrayListProductDao.getInstance();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoGetNoProducts() throws ServletException, IOException {
        servlet.init();
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetWithProducts() throws ServletException, IOException {
        Product product = new Product();
        Long id = 1L;
        product.setId(id);
        productDao.save(product);
        servlet.init();
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
        productDao.delete(id);
    }
}