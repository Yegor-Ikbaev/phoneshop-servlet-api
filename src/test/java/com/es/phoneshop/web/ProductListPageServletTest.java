package com.es.phoneshop.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ProductListPageServlet servlet;

    @Before
    public void setup() {
        servlet = new ProductListPageServlet();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("search")).thenReturn("Samsung");
        when(request.getParameter("order")).thenReturn("desc");
    }

    @Test
    public void testDoGetSortByDescription() throws ServletException, IOException {
        when(request.getParameter("sortBy")).thenReturn("description");
        servlet.init();
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetSortByPrice() throws ServletException, IOException {
        when(request.getParameter("sortBy")).thenReturn("price");
        servlet.init();
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}