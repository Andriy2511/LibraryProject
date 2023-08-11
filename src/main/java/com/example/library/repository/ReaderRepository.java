package com.example.library.repository;

import com.example.library.model.Reader;
import com.example.library.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findAllByUsername(String username);
    Optional<Reader> findAllByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<Reader> findAllByRolesIn(List<Role> roles);
}
