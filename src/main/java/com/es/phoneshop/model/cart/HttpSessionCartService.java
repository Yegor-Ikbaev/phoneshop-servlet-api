package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public class HttpSessionCartService implements CartService {

    private static final int EMPTY_QUANTITY = 0;

    private static final String CART_ATTRIBUTE = "cart";

    private static final CartService INSTANCE = new HttpSessionCartService();

    private HttpSessionCartService() {
    }

    public static CartService getInstance() {
        return INSTANCE;
    }

    @Override
    public Cart getCart(HttpSession httpSession) {
        Cart cart = (Cart) httpSession.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Product product, int quantity) throws LackOfStockException {
        CartItem cartItem = getCartItem(cart, product);
        if (cartItem.getQuantity() == EMPTY_QUANTITY) {
            cart.getCartItems().add(cartItem);
        }
        update(cartItem, quantity);
    }

    private CartItem getCartItem(Cart cart, Product product) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny()
                .orElse(new CartItem(product, EMPTY_QUANTITY));
    }

    private void update(CartItem cartItem, int quantity) throws LackOfStockException {
        int newQuantity = cartItem.getQuantity() + quantity;
        if (newQuantity <= cartItem.getProduct().getStock()) {
            cartItem.setQuantity(newQuantity);
        } else {
            throw new LackOfStockException("There is only " + cartItem.getProduct().getStock() + " products");
        }
    }
}