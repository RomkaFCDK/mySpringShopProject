package com.adamenko.myshop.service;

import com.adamenko.myshop.domain.Order;

public interface OrderService {
    void saveOrder(Order order);
    Order getOrder(Long id);
}
