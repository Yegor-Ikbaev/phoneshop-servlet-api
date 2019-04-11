package com.es.phoneshop.model.checkout;

public interface OrderDao {

    Order getOrder(String id);

    void save(Order order);

    void delete(Order order);
}
