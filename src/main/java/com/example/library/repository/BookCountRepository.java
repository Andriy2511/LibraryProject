package com.example.library.repository;

import com.example.library.model.BookCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCountRepository extends JpaRepository<BookCount, Long> {
}
