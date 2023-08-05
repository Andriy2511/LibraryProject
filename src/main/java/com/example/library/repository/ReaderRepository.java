package com.example.library.repository;

import com.example.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    List<Reader> findAllByUsername(String username);
    List<Reader> findAllByEmail(String email);
}
