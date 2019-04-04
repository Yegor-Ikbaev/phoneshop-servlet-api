package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.exception.QuantityFormatException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

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
        updateCartItem(cartItem, quantity);
    }

    private CartItem getCartItem(Cart cart, Product product) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny()
                .orElse(new CartItem(product, EMPTY_QUANTITY));
    }

    private void updateCartItem(CartItem cartItem, int quantity) throws LackOfStockException {
        int newQuantity = cartItem.getQuantity() + quantity;
        if (newQuantity <= cartItem.getProduct().getStock()) {
            cartItem.setQuantity(newQuantity);
        } else {
            throw new LackOfStockException("There is only " + cartItem.getProduct().getStock() + " products");
        }
    }

    @Override
    public void update(Cart cart, Product product, int quantity) throws LackOfStockException, QuantityFormatException {
        if (quantity < 1) {
            throw new QuantityFormatException("Quantity should be greater 0");
        }
        CartItem cartItem = getCartItem(cart, product);
        cartItem.setQuantity(EMPTY_QUANTITY);
        updateCartItem(cartItem, quantity);
    }

    @Override
    public void delete(Cart cart, Product product) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().equals(product));
    }

    @Override
    public void calculateTotalPrice(Cart cart) {
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.getCartItems().forEach(cartItem -> {
            BigDecimal productPrice = cartItem.getProduct().getPrice();
            BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
            BigDecimal totalPrice = cart.getTotalPrice().add(productPrice.multiply(quantity));
            cart.setTotalPrice(totalPrice);
        });
    }
}