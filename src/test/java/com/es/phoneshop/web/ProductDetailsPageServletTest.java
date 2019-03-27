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

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ProductDetailsPageServlet servlet;

    @Before
    public void setup() {
        servlet = new ProductDetailsPageServlet();
        when(request.getPathInfo()).thenReturn("/1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoGet() throws ServletException, IOException {
        servlet.init();
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCallMethodsInRequest() throws ServletException, IOException {
        servlet.init();
        servlet.doGet(request, response);
        verify(request).getPathInfo();
        verify(request).setAttribute(anyString(), anyString());
    }
}