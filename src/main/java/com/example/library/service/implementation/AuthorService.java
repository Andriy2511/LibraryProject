package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements IAuthorService{

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void addNewAuthor(Author author){
        authorRepository.save(author);
    }

    @Override
    public List<Author> findAllAuthors(){
        return authorRepository.findAll();
    }

    @Override
    public List<Author> findAllAuthorsById(List<Long> authorId){
        return authorRepository.findAllById(authorId);
    }

    @Override
    public Author findAuthorById(Long id){
        return authorRepository.findById(id).get();
    }
}
