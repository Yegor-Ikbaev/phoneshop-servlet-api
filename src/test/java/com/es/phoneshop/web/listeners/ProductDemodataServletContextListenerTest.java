package com.es.phoneshop.web.listeners;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletContextEvent;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {

    private ProductDemodataServletContextListener listener;

    @Mock
    private ServletContextEvent servletContextEvent;

    @Before
    public void setup() {
        listener = new ProductDemodataServletContextListener();
    }

    @Test
    public void testCallMethods() {
        listener.contextInitialized(servletContextEvent);
        listener.contextDestroyed(servletContextEvent);
    }
}