package com.es.phoneshop.model.checkout;

import java.util.regex.Pattern;

public class CheckoutUtil {

    private CheckoutUtil() {
    }

    public static boolean isValidPhone(String numberPhone) {
        String numberPhonePattern = "[+]\\d{12}";
        return Pattern.compile(numberPhonePattern).matcher(numberPhone).matches();
    }
}
