package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public class HttpSessionCartService implements CartService {

    private static final String CART_ATTRIBUTE = "cart";

    private static final CartService INSTANCE = new HttpSessionCartService();

    private HttpSessionCartService() {
    }

    public CartService getInstance() {
        return INSTANCE;
    }

    @Override
    public Cart getCartFromSource(Object source) {
        HttpSession session = (HttpSession) source;
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Product product, int quantity) throws LackOfStockException {
        if (product.getStock() < quantity) {
            throw new LackOfStockException("There is only " + product.getStock() + " products");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity should be greater than 1");
        }
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny()
                .orElse(null);
        if (cartItem == null) {
            cart.getCartItems().add(new CartItem(product, quantity));
        } else {
            update(cartItem, quantity);
        }
    }

    private void update(CartItem cartItem, int quantity) throws LackOfStockException {
        int newValue = cartItem.getQuantity() + quantity;
        if (newValue > cartItem.getProduct().getStock()) {
            throw new LackOfStockException("There is only " + cartItem.getProduct().getStock() + " products");
        } else {
            cartItem.setQuantity(newValue);
        }
    }
}