package com.es.phoneshop.model.storage;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerMemoryServiceTest {

    private CustomerMemoryService customerMemoryService;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Product product;

    @Before
    public void setup() {
        customerMemoryService = HttpSessionCustomerMemory.getInstance();
    }

    @Test
    public void testGetExistingStorage() {
        Storage storage = new Storage();
        when(httpSession.getAttribute(anyString())).thenReturn(storage);
        assertEquals(storage, customerMemoryService.getStorageFromSource(httpSession));
    }

    @Test
    public void testGetNotExistingStorage() {
        assertNotNull(customerMemoryService.getStorageFromSource(httpSession));
    }

    @Test
    public void testUpdateWithoutExistingProduct() {
        Storage storage = new Storage();
        customerMemoryService.update(storage, product);
        assertTrue(storage.getViewedProducts().contains(product));
    }

    @Test
    public void testUpdateWithExistingProduct() {
        Storage storage = new Storage();
        customerMemoryService.update(storage, product);
        customerMemoryService.update(storage, product);
        assertEquals(1, storage.getViewedProducts().size());
    }

    @Test
    public void testGetRecentlyViewedProducts() {
        Storage storage = new Storage();
        customerMemoryService.update(storage, product);
        assertTrue(!customerMemoryService.getRecentlyViewedProducts(storage).isEmpty());
    }
}