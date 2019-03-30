package com.es.phoneshop.web.listeners;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletContextEvent;

import static org.junit.Assert.assertTrue;

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
        ProductDao productDao = ArrayListProductDao.getInstance();
        listener.contextInitialized(servletContextEvent);
        listener.contextDestroyed(servletContextEvent);
        assertTrue(!productDao.findProducts().isEmpty());
    }
}