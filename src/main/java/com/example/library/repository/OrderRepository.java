package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.Order;
import com.example.library.model.Reader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByReader(Reader reader);
    List<Order> findAllByBookAndIsReturned(Book book, Boolean isReturned);
    List<Order> findAllByReader(Reader reader, Pageable pageable);
    Long countOrdersByReader(Reader reader);

    @Query("SELECT o FROM Order o WHERE o.returnDate < CURRENT_DATE AND o.isReturned = false")
    List<Order> fundOverdueOrders();
    @Modifying
    @Query("UPDATE Order o SET o.isReturned = true WHERE o.id = :userOrderId")
    void setStatusReturnedTrueForOrderById(@Param("userOrderId") Long userOrderId);
}
