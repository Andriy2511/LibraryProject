package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Order;
import com.example.library.model.Reader;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The {@code IOrderService} interface defines methods for managing order-related operations
 * within the library management system.
 */
public interface IOrderService {

    /**
     * Retrieves a list of all orders present in the database.
     *
     * @return A list containing all orders.
     */
    List<Order> showAllOrders();

    /**
     * Retrieves a list of orders associated with a specific reader.
     *
     * @param reader The reader for whom orders are to be retrieved.
     * @return A list of orders associated with the given reader.
     */
    List<Order> findOrdersByReader(Reader reader);

    /**
     * Saves an order to the database.
     *
     * @param order The order to be saved.
     */
    void saveOrder(Order order);

    Long getOrdersCount();

    Long getOrdersCountByReader(Reader reader);

    List<Order> findOrdersByReaderWithPagination(Reader reader, int page, int pageSize);

    List<Order> showAllOrdersWithPagination(int page, int pageSize);

    List<Order> getOverdueOrders();

    void setOrderStatusReturned(Long userOrderId);

    List<Order> getAllOrdersForBookByReturnedStatus(Book book, Boolean isReturned);

    Order getOrderById(Long orderId);
}