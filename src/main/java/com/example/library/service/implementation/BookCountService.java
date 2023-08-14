package com.example.library.service;

import com.example.library.model.BookCount;
import com.example.library.repository.BookCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCountService implements IBookCountService{

    private final BookCountRepository bookCountRepository;

    @Autowired
    public BookCountService(BookCountRepository bookCountRepository) {
        this.bookCountRepository = bookCountRepository;
    }

    @Override
    public void saveBookCount(BookCount bookCount){
        bookCountRepository.save(bookCount);
    }
}
