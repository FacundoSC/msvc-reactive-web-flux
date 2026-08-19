package org.faccordoba.springcloud.msvc.msvc_orders.service;

import org.faccordoba.springcloud.msvc.msvc_orders.model.Order;

public interface OrderService {
    void createOrder(Order order);
}
