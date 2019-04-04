package com.es.phoneshop.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Product product;

    private ProductDetailsPageServlet servlet;

    @Before
    public void setup() {
        servlet = new ProductDetailsPageServlet();
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
        when(product.getStock()).thenReturn(100);
        when(product.getId()).thenReturn((long) 1);
        when(product.getPrice()).thenReturn(new BigDecimal(1));
        ArrayListProductDao.getInstance().save(product);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.init();
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
        ArrayListProductDao.getInstance().delete(product.getId());
    }

    @Test
    public void testDoPost() throws IOException {
        when(request.getParameter("quantity")).thenReturn("1");
        servlet.init();
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
        ArrayListProductDao.getInstance().delete(product.getId());
    }

    @Test
    public void testDoPostWithQuantityNotNumber() throws IOException {
        when(request.getParameter("quantity")).thenReturn("symbol");
        servlet.init();
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
        ArrayListProductDao.getInstance().delete(product.getId());
    }

    @Test
    public void testDoPostWithNegativeQuantity() throws IOException {
        when(request.getParameter("quantity")).thenReturn("-1");
        servlet.init();
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
        ArrayListProductDao.getInstance().delete(product.getId());
    }

    @Test
    public void testDoPostWithStockLessQuantity() throws IOException {
        when(request.getParameter("quantity")).thenReturn("101");
        servlet.init();
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
        ArrayListProductDao.getInstance().delete(product.getId());
    }
}