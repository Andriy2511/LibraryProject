package com.example.library.service;

import com.example.library.model.Order;
import com.example.library.model.Reader;
import com.example.library.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> showAllOrders(){
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findOrdersByReader(Reader reader){
        return orderRepository.findAllByReader(reader);
    }

    @Override
    public void saveOrder(Order order){
        orderRepository.save(order);
    }
}
