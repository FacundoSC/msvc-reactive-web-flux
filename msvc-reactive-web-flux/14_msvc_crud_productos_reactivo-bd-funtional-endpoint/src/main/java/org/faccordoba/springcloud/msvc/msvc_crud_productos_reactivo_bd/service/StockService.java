package org.faccordoba.springcloud.msvc.msvcstock.service;

import org.faccordoba.springcloud.msvc.msvcstock.model.Order;

public interface StockService {
    void updateStock(Order order);
}
