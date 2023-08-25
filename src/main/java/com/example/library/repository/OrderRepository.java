package com.example.library.repository;

import com.example.library.model.Order;
import com.example.library.model.Reader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByReader(Reader reader);
    List<Order> findAllByReader(Reader reader, Pageable pageable);
    Long countOrdersByReader(Reader reader);

    @Query("SELECT o FROM Order o WHERE o.returnDate < CURRENT_DATE AND o.isReturned = false")
    List<Order> fundOverdueOrders();
}
