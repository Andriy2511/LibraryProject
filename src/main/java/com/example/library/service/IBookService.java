package com.example.library.service;

import com.example.library.model.Book;
import org.springframework.data.domain.Page;

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


    /**
     * Retrieves a paginated and sorted list of books by sorted field.
     *
     * @param page        The page number to retrieve.
     * @param pageSize    The maximum number of books to include on each page.
     * @param sortedField The field by which the books should be sorted.
     * @return A Page object containing a list of Book items within the specified page and page size.
     */
    Page<Book> findBooksWithPaginationAndSorting(int page, int pageSize, String sortedField);

    /**
     * Retrieves the total count of all books in the database.
     *
     * @return The total number of books.
     */
    Long selectCountOfBooks();

    /**
     * Retrieves the total count of all books in the database.
     *
     * @return The total number of books.
     */
    Long getBooksCount();

    /**
     * Retrieves the total count of all books in the database.
     *
     * @return The total number of books.
     * @deprecated Use {@link #selectCountOfBooks()} instead.
     */
    List<Book> findAllBooksWithPagination(int page, int pageSize);

    /**
     * Retrieves the count of available books.
     *
     * @param book The book for which to count available copies.
     * @return The count of available copies for the given book.
     */
    Integer getCountOfAvailableBooks(Book book);
}