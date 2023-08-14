package com.example.library.service;

import com.example.library.model.BookCount;

/**
 * The {@code IBookCountService} interface defines methods for managing book count information
 * within the library management system.
 */
public interface IBookCountService {

    /**
     * Saves the book count information to the database.
     *
     * @param bookCount The book count object containing information about the book quantities.
     */
    void saveBookCount(BookCount bookCount);
}