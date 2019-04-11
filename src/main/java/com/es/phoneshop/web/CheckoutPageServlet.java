package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.checkout.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CheckoutPageServlet extends HttpServlet {

    private static final String FIRST_NAME_PARAMETER = "firstName";

    private static final String LAST_NAME_PARAMETER = "lastName";

    private static final String PHONE_PARAMETER = "phone";

    private static final String ADDRESS_PARAMETER = "address";

    private CartService cartService;
    
    private OrderService orderService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("cart", cart);
        request.setAttribute("deliveryModes", DeliveryMode.getDeliveryModes());
        request.setAttribute("dates", DeliveryDate.getDeliveryDates());
        request.setAttribute("paymentMethods", PaymentMethod.getPaymentMethods());
        request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<Order> optionalOrder = createOrder(request);
        if (optionalOrder.isPresent()) {
            String orderOverviewPage = request.getContextPath() + "/order/overview/" + optionalOrder.get().getId();
            response.sendRedirect(orderOverviewPage);
        } else {
            fillOrderInformation(request);
            doGet(request, response);
        }
    }

    private Optional<Order> createOrder(HttpServletRequest request) {
        Optional<ContactDetails> optionalContactDetails = getContactsDetailsFromRequest(request);
        Optional<DeliveryDetails> optionalDeliveryDetails = getDeliveryDetailsFromRequest(request);
        PaymentMethod paymentMethod = getPaymentMethodFormRequest(request);
        if (!optionalContactDetails.isPresent() || !optionalDeliveryDetails.isPresent()) {
            return Optional.empty();
        } else {
            Order order = orderService.createOrder(cartService.getCart(request));
            orderService.placeOrder(order, optionalContactDetails.get(),
                    optionalDeliveryDetails.get(), paymentMethod);
            return Optional.of(order);
        }
    }

    private void fillOrderInformation(HttpServletRequest request) {
        request.setAttribute(FIRST_NAME_PARAMETER, request.getParameter(FIRST_NAME_PARAMETER));
        request.setAttribute(LAST_NAME_PARAMETER, request.getParameter(LAST_NAME_PARAMETER));
        request.setAttribute(PHONE_PARAMETER, request.getParameter(PHONE_PARAMETER));
        request.setAttribute(ADDRESS_PARAMETER, request.getParameter(ADDRESS_PARAMETER));
    }

    private Optional<ContactDetails> getContactsDetailsFromRequest(HttpServletRequest request) {
        boolean isSuccessful = true;
        String firstName = request.getParameter(FIRST_NAME_PARAMETER);
        if (isIncorrect(firstName)) {
            isSuccessful = false;
            request.setAttribute("firstNameError", "Incorrect first name");
        }
        String lastName = request.getParameter(LAST_NAME_PARAMETER);
        if (isIncorrect(lastName)) {
            isSuccessful = false;
            request.setAttribute("lastNameError", "Incorrect last name");
        }
        String phone = request.getParameter(PHONE_PARAMETER);
        if (isIncorrect(phone) || !CheckoutUtil.isValidPhone(phone)) {
            isSuccessful = false;
            request.setAttribute("phoneError", "Incorrect phone");
        }
        if (isSuccessful) {
            return Optional.of(new ContactDetails(firstName, lastName, phone));
        } else {
            return Optional.empty();
        }
    }

    private Optional<DeliveryDetails> getDeliveryDetailsFromRequest(HttpServletRequest request) {
        boolean isSuccessful = true;
        DeliveryMode deliveryMode = DeliveryMode.getDeliveryMode(request.getParameter("deliveryMode"));
        DeliveryDate deliveryDate = DeliveryDate.getDeliveryDate(request.getParameter("date"));
        String address = request.getParameter(ADDRESS_PARAMETER);
        if (isIncorrect(address)) {
            isSuccessful = false;
            request.setAttribute("addressError", "Incorrect address");
        }
        if (isSuccessful) {
            return Optional.of(new DeliveryDetails(deliveryMode, deliveryDate, address));
        } else {
            return Optional.empty();
        }
    }

    private PaymentMethod getPaymentMethodFormRequest(HttpServletRequest request) {
        return PaymentMethod.getPaymentMethod(request.getParameter("paymentMethod"));
    }

    private boolean isIncorrect(String value) {
        return value == null || value.isEmpty();
    }
}
