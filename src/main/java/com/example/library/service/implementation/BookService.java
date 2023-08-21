package com.example.library.service.implementation;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

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

    @Override
    public List<Book> findBooksWithSorting(String sortedField){
        return bookRepository.findAll(Sort.by(sortedField));
    }

    @Override
    public Page<Book> findBooksWithPagination(int page, int pageSize){
        return bookRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<Book> findBooksWithPaginationAndSorting(int page, int pageSize, String sortedField){
        return bookRepository.findAll(PageRequest.of(page, pageSize).withSort(Sort.by(sortedField)));
    }

    @Override
    public Long selectCountOfBooks(){
        return bookRepository.count();
    }
}
