package com.example.library.repository;

import com.example.library.model.FineAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FineAmountRepository extends JpaRepository<FineAmount, Long> {
}
