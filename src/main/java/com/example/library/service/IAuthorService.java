package com.example.library.service;

import com.example.library.model.Author;

import java.util.List;

/**
 * The {@code IAuthorService} interface defines methods for interacting with author-related operations
 * within the library management system.
 */
public interface IAuthorService {

    /**
     * Adds a new author to the library's collection.
     *
     * @param author The author to be added.
     */
    void addNewAuthor(Author author);

    /**
     * Retrieves a list of all authors present in the library.
     *
     * @return A list containing all authors.
     */
    List<Author> findAllAuthors();

    /**
     * Retrieves a list of authors based on a list of author IDs.
     *
     * @param authorId A list of author IDs for which authors are to be retrieved.
     * @return A list of authors corresponding to the given author IDs.
     */
    List<Author> findAllAuthorsById(List<Long> authorId);

    /**
     * Retrieves an author by their unique identifier.
     *
     * @param id The unique identifier of the author to be retrieved.
     * @return The author corresponding to the given ID, or {@code null} if not found.
     */
    Author findAuthorById(Long id);
}