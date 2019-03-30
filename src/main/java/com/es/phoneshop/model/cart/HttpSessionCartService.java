package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionCartService implements CartService {

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
        Optional<CartItem> optionalCartItem = findCartItem(cart, product);
        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
        } else {
            int emptyQuantity = 0;
            cartItem = new CartItem(product, emptyQuantity);
            cart.getCartItems().add(cartItem);
        }
        update(cartItem, quantity);
    }

    private Optional<CartItem> findCartItem(Cart cart, Product product) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny();
    }

    private void update(CartItem cartItem, int quantity) throws LackOfStockException {
        int newValue = cartItem.getQuantity() + quantity;
        if (newValue <= cartItem.getProduct().getStock()) {
            cartItem.setQuantity(newValue);
        } else {
            throw new LackOfStockException("There is only " + cartItem.getProduct().getStock() + " products");
        }
    }
}