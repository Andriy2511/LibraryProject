package com.example.library.service.implementation;

import com.example.library.model.Book;
import com.example.library.model.Order;
import com.example.library.model.Reader;
import com.example.library.repository.OrderRepository;
import com.example.library.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService implements IOrderService {

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

    @Override
    public Long getOrdersCount() {
        return orderRepository.count();
    }

    @Override
    public Long getOrdersCountByReader(Reader reader) {
        return orderRepository.countOrdersByReader(reader);
    }

    @Override
    public List<Order> findOrdersByReaderWithPagination(Reader reader, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return orderRepository.findAllByReader(reader, pageable);
    }

    @Override
    public List<Order> showAllOrdersWithPagination(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return orderRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Order> getOverdueOrders() {
        return orderRepository.fundOverdueOrders();
    }

    @Override
    @Transactional
    public void setOrderStatusReturned(Long userOrderId) {
        orderRepository.setStatusReturnedTrueForOrderById(userOrderId);
    }

    @Override
    public List<Order> getAllOrdersForBookByReturnedStatus(Book book, Boolean isReturned) {
        return orderRepository.findAllByBookAndIsReturned(book, isReturned);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findOrderById(orderId).get();
    }
}
