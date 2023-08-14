package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService{

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    @Override
    public void addBook(Book book){
        bookRepository.save(book);
    }

    @Override
    public Book findBookById(Long id){
        return bookRepository.findById(id).get();
    }
}
