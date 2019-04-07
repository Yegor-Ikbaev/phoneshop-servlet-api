package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
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

    private CartPageServlet servlet;

    @Before
    public void setup(){
        servlet = new CartPageServlet();
        String[] productIds = {"1", "1", "1"};
        when(request.getParameterValues("productId")).thenReturn(productIds);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
        when(product.getStock()).thenReturn(100);
        when(product.getId()).thenReturn((long) 1);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.init();
        servlet.doGet(request, response);
        verify(request).setAttribute(anyString(), any());
        verify(request.getRequestDispatcher(anyString())).forward(request, response);
    }

    @Test
    public void testDoPostSuccessful() throws IOException, ServletException {
        servlet.init();
        ArrayListProductDao.getInstance().save(product);
        String[] quantities = {"1", "1", "1"};
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
        ArrayListProductDao.getInstance().delete(product.getId());
    }

    @Test
    public void testDoPostUnsuccessful() throws ServletException, IOException {
        servlet.init();
        ArrayListProductDao.getInstance().save(product);
        String[] quantities = {"-1", "not a number", "100"};
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        servlet.doPost(request, response);
        verify(request, times(4)).setAttribute(anyString(), any());
        verify(request.getRequestDispatcher(anyString())).forward(request, response);
        ArrayListProductDao.getInstance().delete(product.getId());
    }
}
