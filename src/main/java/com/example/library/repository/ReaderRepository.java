package com.example.library.repository;

import com.example.library.model.Reader;
import com.example.library.model.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findAllByUsername(String username);
    Optional<Reader> findAllByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Long countReadersByRoles(Role role);
    List<Reader> findAllByRolesIn(List<Role> roles);
    List<Reader> findAllByRolesIn(List<Role> roles, Pageable pageable);
}
