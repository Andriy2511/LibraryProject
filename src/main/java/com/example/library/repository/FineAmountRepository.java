package com.example.library.repository;

import com.example.library.model.FineAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineAmountRepository extends JpaRepository<FineAmount, Long> {
}
