package com.es.phoneshop.web.listeners;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

public class ProductDemodataServletContextListenerTest {
    
    private ProductDemodataServletContextListener listener;

    @Before
    public void setup() {
	listener = new ProductDemodataServletContextListener();
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
	listener.contextInitialized(null);
	listener.contextDestroyed(null);
    }
}