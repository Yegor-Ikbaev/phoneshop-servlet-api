package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
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
    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Product product, int quantity) throws LackOfStockException, IllegalQuantityException {
        CartItem cartItem = getCartItem(cart, product);
        updateCartItem(cartItem, quantity);
        if (!cart.getCartItems().contains(cartItem)) {
            cart.getCartItems().add(cartItem);
        }
    }

    private CartItem getCartItem(Cart cart, Product product) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny()
                .orElse(new CartItem(product, EMPTY_QUANTITY));
    }

    private void updateCartItem(CartItem cartItem, int quantity) throws LackOfStockException, IllegalQuantityException {
        if (quantity < 1) {
            throw new IllegalQuantityException("Quantity should be greater 0");
        }
        int newQuantity = cartItem.getQuantity() + quantity;
        if (newQuantity <= cartItem.getProduct().getStock()) {
            cartItem.setQuantity(newQuantity);
        } else {
            throw new LackOfStockException("There is only " + cartItem.getProduct().getStock() + " products");
        }
    }

    @Override
    public void update(Cart cart, Product product, int quantity) throws LackOfStockException, IllegalQuantityException {
        CartItem cartItem = getCartItem(cart, product);
        cartItem.setQuantity(EMPTY_QUANTITY);
        updateCartItem(cartItem, quantity);
    }

    @Override
    public void delete(Cart cart, Product product) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().equals(product));
    }

    @Override
    public void clear(Cart cart) {
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }

    @Override
    public void calculateTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }
}