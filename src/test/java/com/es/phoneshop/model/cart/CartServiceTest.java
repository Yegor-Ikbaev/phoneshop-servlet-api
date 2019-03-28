package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @Mock
    private HttpSession httpSession;

    @Mock
    private Product product;

    private CartService cartService;

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        when(product.getStock()).thenReturn(10);
    }

    @Test
    public void testGetExistedCart() {
        Cart cart = new Cart();
        when(httpSession.getAttribute(anyString())).thenReturn(cart);
        assertEquals(cart, cartService.getCartFromSource(httpSession));
    }

    @Test
    public void testGetNotExistedCart() {
        assertNotNull(cartService.getCartFromSource(httpSession));
    }

    @Test(expected = LackOfStockException.class)
    public void testAddWithStockLessQuantity() throws LackOfStockException {
        cartService.add(new Cart(), product, 15);
    }

    @Test
    public void testAddWithoutSameCartItem() throws LackOfStockException {
        Cart cart = new Cart();
        cartService.add(cart, product, 8);
        assertTrue(!cart.getCartItems().isEmpty());
    }

    @Test
    public void testAddWithSameCartItem() throws LackOfStockException {
        CartItem cartItem = new CartItem(product, 1);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        int quantity = 8;
        cartService.add(cart, product, quantity);
        assertEquals(cartItem.getQuantity(), quantity + 1);
    }

    @Test(expected = LackOfStockException.class)
    public void testAddWithSameCartItemAndStockLessQuantity() throws LackOfStockException {
        CartItem cartItem = new CartItem(product, 3);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        cartService.add(cart, product, 8);
    }
}