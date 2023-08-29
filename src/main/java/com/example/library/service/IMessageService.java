package com.example.library.service;

import com.example.library.model.Message;
import com.example.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The {@code IMessageService} interface defines methods for interacting with message-related operations
 * within the library management system.
 */
public interface IMessageService {
    /**
     * Retrieves a paginated list of messages associated with a specific reader.
     *
     * @param reader   The reader for whom to retrieve messages.
     * @param page     The page number (starting from 1) to retrieve.
     * @param pageSize The maximum number of messages to include on each page.
     * @return A list of Message objects associated with the specified reader, within the specified page and page size.
     */
    List<Message> findMessagesByReaderWithPagination(Reader reader, int page, int pageSize);

    /**
     * Retrieves the total count of messages associated with a specific reader.
     *
     * @param reader The reader for whom to count messages.
     * @return The total number of messages associated with the specified reader.
     */
    Long getMessagesCountByReader(Reader reader);

    /**
     * Retrieves a message by it's unique identifier.
     *
     * @param messageId The ID of the message to retrieve.
     * @return The Message object corresponding to the provided ID, or null if not found.
     */
    Message findMessageById(Long messageId);

    /**
     * Saves a message into the system.
     *
     * @param message The message object to be saved.
     */
    void saveMessage(Message message);
}
