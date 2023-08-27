package com.example.library.service.implementation;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final OrderService orderService;

    @Autowired
    public BookService(BookRepository bookRepository, OrderService orderService) {
        this.bookRepository = bookRepository;
        this.orderService = orderService;
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
    public Page<Book> findBooksWithPaginationAndSorting(int page, int pageSize, String sortedField){
        return bookRepository.findAll(PageRequest.of(page, pageSize).withSort(Sort.by(sortedField)));
    }

    @Override
    public Long selectCountOfBooks(){
        return bookRepository.count();
    }

    @Override
    public Long getBooksCount() {
        return bookRepository.count();
    }

    @Override
    public List<Book> findAllBooksWithPagination(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public Integer getCountOfAvailableBooks(Book book) {
        int bookCount;
        if(book.getBookCount() != null && book.getBookCount().getCount() != null && book.getBookCount().getCount() > 0)
            bookCount = book.getBookCount().getCount() - orderService.getAllOrdersForBookByReturnedStatus(book, false).size();
        else
            bookCount = 0;
        return bookCount;
    }
}
