package com.example.library.repository;

import com.example.library.model.Message;
import com.example.library.model.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findMessageByReader(Reader reader, Pageable pageable);
    Long countMessageByReader(Reader reader);
}
