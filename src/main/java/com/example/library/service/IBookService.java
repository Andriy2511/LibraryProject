package com.example.library.service;

import com.example.library.model.Book;

import java.util.List;

/**
 * The {@code IBookService} interface defines methods for interacting with book-related operations
 * within the library management system.
 */
public interface IBookService {

    /**
     * Retrieves a list of all books present in the library.
     *
     * @return A list containing all books.
     */
    List<Book> findAllBooks();

    /**
     * Adds a new book to the library's collection.
     *
     * @param book The book to be added.
     */
    void addBook(Book book);

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param id The unique identifier of the book to be retrieved.
     * @return The book corresponding to the given ID, or {@code null} if not found.
     */
    Book findBookById(Long id);
}