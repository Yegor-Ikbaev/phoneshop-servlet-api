package com.es.phoneshop.model.checkout;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CheckoutUtilTest {

    @Test
    public void testValidPhone() {
        assertTrue(CheckoutUtil.isValidPhone("+123456789012"));
    }

    @Test
    public void testInvalidPhoneWithoutPlusSymbol() {
        assertTrue(!CheckoutUtil.isValidPhone("123456789012"));
    }

    @Test
    public void testInvalidPhoneWithIncorrectSymbols() {
        assertTrue(!CheckoutUtil.isValidPhone("+12345678901a"));
    }

    @Test
    public void testInvalidPhoneWithSymbolsAmountLessNeeded() {
        assertTrue(!CheckoutUtil.isValidPhone("+12345678901"));
    }

    @Test
    public void testInvalidPhoneWithSymbolsAmountGreaterNeeded() {
        assertTrue(!CheckoutUtil.isValidPhone("+1234567890123"));
    }
}
