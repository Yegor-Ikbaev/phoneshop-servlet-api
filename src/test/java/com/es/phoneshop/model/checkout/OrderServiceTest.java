package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private Order order;

    @Mock
    private Cart cart;

    @Mock
    private ContactDetails contactDetails;

    @Mock
    private DeliveryDetails deliveryDetails;

    @Before
    public void setup() {
        orderService = OrderServiceImpl.getInstance();
        when(order.getId()).thenReturn(UUID.randomUUID().toString());
        when(order.getPriceOfProducts()).thenReturn(BigDecimal.ZERO);
        when(order.getDeliveryDetails()).thenReturn(deliveryDetails);
        when(deliveryDetails.getPrice()).thenReturn(BigDecimal.ZERO);
    }

    @Test
    public void testCreateOrder() {
        assertNotNull(orderService.createOrder(cart));
    }

    @Test
    public void testPlaceOrder() {
        orderService.placeOrder(order, contactDetails, deliveryDetails, PaymentMethod.CASH);
        verify(order).setId(anyString());
        verify(order).setContactDetails(contactDetails);
        verify(order).setDeliveryDetails(deliveryDetails);
        verify(order).setPaymentMethod(PaymentMethod.CASH);
        verify(order).setTotalPrice(BigDecimal.ZERO);
    }

}
