package com.example.library.service;

import com.example.library.model.Message;
import com.example.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMessageService {
    List<Message> findMessagesByReaderWithPagination(Reader reader, int page, int pageSize);

    Long getMessagesCountByReader(Reader reader);

    Message findMessageById(Long messageId);
}
