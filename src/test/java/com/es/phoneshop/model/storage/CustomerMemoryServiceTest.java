package com.es.phoneshop.model.storage;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerMemoryServiceTest {
    @Mock
    private HttpSession httpSession;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Product product;

    private CustomerMemoryService customerMemoryService;

    private Storage storage;

    @Before
    public void setup() {
        customerMemoryService = HttpSessionCustomerMemory.getInstance();
        storage = new Storage();
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void testGetExistingStorage() {
        when(httpSession.getAttribute(anyString())).thenReturn(storage);
        assertEquals(storage, customerMemoryService.getStorage(request));
    }

    @Test
    public void testGetNotExistingStorage() {
        assertNotNull(customerMemoryService.getStorage(request));
    }

    @Test
    public void testUpdateWithoutExistingProduct() {
        customerMemoryService.update(storage, product);
        assertTrue(storage.getViewedProducts().contains(product));
    }

    @Test
    public void testUpdateWithExistingProduct() {
        customerMemoryService.update(storage, product);
        customerMemoryService.update(storage, product);
        assertEquals(1, storage.getViewedProducts().size());
    }

    @Test
    public void testGetRecentlyViewedProducts() {
        customerMemoryService.update(storage, product);
        assertTrue(!customerMemoryService.getRecentlyViewedProducts(storage).isEmpty());
    }
}