package com.example.library.service;

import com.example.library.model.Fine;
import com.example.library.model.Reader;

import java.util.List;

/**
 * The {@code IFineService} interface defines methods for interacting with fine-related operations
 * within the library management system.
 */
public interface IFineService {

    /**
     * Saves a fine into the database.
     *
     * @param fine The fine object to be saved.
     */
    void saveFine(Fine fine);

    /**
     * Retrieves a paginated list of all fines in the system.
     *
     * @param page     The page number to retrieve.
     * @param pageSize The maximum number of fines to include on each page.
     * @return A list of Fine objects within the specified page and page size.
     */
    List<Fine> findAllFinesWithPagination(int page, int pageSize);

    /**
     * Retrieves the total count of all fines in the database.
     *
     * @return The total number of fines.
     */
    Long getFinesCount();

    /**
     * Retrieves the total count of fines associated with a specific reader.
     *
     * @param reader The reader for whom to count fines.
     * @return The total number of fines associated with the specified reader.
     */
    Long getFinesCountByReader(Reader reader);

    /**
     * Retrieves a paginated list of fines associated with a specific reader.
     *
     * @param page     The page number (starting from 1) to retrieve.
     * @param pageSize The maximum number of fines to include on each page.
     * @param readerId The ID of the reader for whom to retrieve fines.
     * @return A list of Fine objects associated with the specified reader, within the specified page and page size.
     */
    List<Fine> findAllFinesByReaderWithPagination(int page, int pageSize, Long readerId);

    /**
     * Retrieves a fine by its unique identifier.
     *
     * @param fineId The ID of the fine to retrieve.
     * @return The Fine object corresponding to the provided ID, or null if not found.
     */
    Fine getFineById(Long fineId);

    /**
     * Updates the details of a fine in the database.
     *
     * @param fine The fine object with updated details.
     */
    void updateFine(Fine fine);
}
