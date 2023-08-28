package com.example.library.repository;

import com.example.library.model.Fine;
import com.example.library.model.Reader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO fines (order_id, fine_cost, is_paid)" +
            "VALUES (:orderId, :fineCost, :isPaid)", nativeQuery = true)
    void insertFine(@Param("orderId") Long orderId, @Param("fineCost") Double fineCost, @Param("isPaid") boolean isPaid);

    @Transactional
    @Modifying
    @Query("UPDATE Fine f SET f.fineCost=:fineCost, f.isPaid=:isPaid WHERE f.order.id = :orderId")
    void updateFine(@Param("orderId") Long orderId, @Param("fineCost") Double fineCost, @Param("isPaid") boolean isPaid);

    @Query("SELECT f FROM Fine f LEFT JOIN Order o ON f.order.id = o.id " +
            "WHERE o.reader.id = :readerId AND o.isReturned = false")
    List<Fine> findUnreturnedFinesForReader(@Param("readerId") Long readerId);

    @Query("SELECT f FROM Fine f LEFT JOIN Order o ON f.order.id = o.id " +
            "WHERE o.reader.id = :readerId")
    List<Fine> findAllFinesForReaderById(@Param("readerId") Long readerId, Pageable pageable);

    Optional<Fine> findFirstFineByOrderId(Long orderId);
}
