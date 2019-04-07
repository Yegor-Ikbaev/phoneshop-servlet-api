package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;

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
    private HttpServletRequest request;

    @Mock
    private Product product;

    private CartService cartService;

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        when(request.getSession()).thenReturn(httpSession);
        when(product.getStock()).thenReturn(10);
        when(product.getPrice()).thenReturn(new BigDecimal(100));
    }

    @Test
    public void testGetExistingCart() {
        Cart cart = new Cart();
        when(httpSession.getAttribute(anyString())).thenReturn(cart);
        assertEquals(cart, cartService.getCart(request));
    }

    @Test
    public void testGetNotExistingCart() {
        assertNotNull(cartService.getCart(request));
    }

    @Test(expected = LackOfStockException.class)
    public void testAddWithStockLessQuantity() throws LackOfStockException, IllegalQuantityException {
        cartService.add(new Cart(), product, 15);
    }

    @Test
    public void testAddWithoutSameCartItem() throws LackOfStockException, IllegalQuantityException {
        Cart cart = new Cart();
        cartService.add(cart, product, 8);
        assertTrue(!cart.getCartItems().isEmpty());
    }

    @Test
    public void testAddWithSameCartItem() throws LackOfStockException, IllegalQuantityException {
        CartItem cartItem = new CartItem(product, 1);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        int quantity = 8;
        cartService.add(cart, product, quantity);
        assertEquals(cartItem.getQuantity(), quantity + 1);
    }

    @Test(expected = LackOfStockException.class)
    public void testAddWithSameCartItemAndStockLessQuantity() throws LackOfStockException, IllegalQuantityException {
        CartItem cartItem = new CartItem(product, 3);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        cartService.add(cart, product, 8);
    }

    @Test(expected = IllegalQuantityException.class)
    public void testUpdateWithIllegalQuantity() throws IllegalQuantityException, LackOfStockException{
        int illegalQuantity = 0;
        cartService.update(new Cart(), product, illegalQuantity);
    }

    @Test
    public void testUpdate() throws IllegalQuantityException, LackOfStockException{
        int quantity = 5;
        CartItem cartItem = new CartItem(product, quantity);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        int newQuantity = 10;
        cartService.update(cart, product, newQuantity);
        assertEquals(cartItem.getQuantity(), newQuantity);
    }

    @Test(expected = LackOfStockException.class)
    public void testUpdateWithBigStock() throws IllegalQuantityException, LackOfStockException{
        int quantity = 5;
        CartItem cartItem = new CartItem(product, quantity);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        int newQuantity = 11;
        cartService.update(cart, product, newQuantity);
    }

    @Test
    public void testDeleteCartItem() {
        int quantity = 5;
        Cart cart = new Cart();
        cart.getCartItems().add(new CartItem(product, quantity));
        cartService.delete(cart, product);
        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    public void testDeleteNotExistingCartItem() {
        Cart cart = new Cart();
        cartService.delete(cart, product);
        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    public void testCalculateTotalPrice() {
        Cart cart = new Cart();
        cart.getCartItems().add(new CartItem(product, 1));
        cart.getCartItems().add(new CartItem(product, 2));
        cartService.calculateTotalPrice(cart);
        assertEquals(new BigDecimal(300), cart.getTotalPrice());
    }
}