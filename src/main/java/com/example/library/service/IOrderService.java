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

    /**
     * Retrieves the total count of all orders in the database.
     *
     * @return The total number of orders.
     */
    Long getOrdersCount();

    /**
     * Retrieves the total count of orders associated with a specific reader.
     *
     * @param reader The reader for whom to count orders.
     * @return The total number of orders associated with the specified reader.
     */
    Long getOrdersCountByReader(Reader reader);

    /**
     * Retrieves a paginated list of orders associated with a specific reader.
     *
     * @param reader   The reader for whom to retrieve orders.
     * @param page     The page number (starting from 1) to retrieve.
     * @param pageSize The maximum number of orders to include on each page.
     * @return A list of orders associated with the specified reader, within the specified page and page size.
     */
    List<Order> findOrdersByReaderWithPagination(Reader reader, int page, int pageSize);

    /**
     * Retrieves a paginated list of all orders present in the database.
     *
     * @param page     The page number (starting from 1) to retrieve.
     * @param pageSize The maximum number of orders to include on each page.
     * @return A list of all orders within the specified page and page size.
     */
    List<Order> showAllOrdersWithPagination(int page, int pageSize);

    /**
     * Retrieves a list of overdue orders.
     *
     * @return A list of orders that are overdue.
     */
    List<Order> getOverdueOrders();

    /**
     * Sets the status of an order as "returned."
     *
     * @param userOrderId The ID of the order to be marked as returned.
     */
    void setOrderStatusReturned(Long userOrderId);

    /**
     * Retrieves a list of orders associated with a specific book and return status.
     *
     * @param book      The book for which to retrieve orders.
     * @param isReturned The return status of the orders.
     * @return A list of orders associated with the specified book and return status.
     */
    List<Order> getAllOrdersForBookByReturnedStatus(Book book, Boolean isReturned);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The Order object corresponding to the provided ID, or null if not found.
     */
    Order getOrderById(Long orderId);
}