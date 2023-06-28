package com.adamenko.myshop.service;

import com.adamenko.myshop.dao.OrderRepository;
import com.adamenko.myshop.domain.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;


    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderRepository.save(order);

    }



    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
