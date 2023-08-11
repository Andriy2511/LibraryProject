package com.example.library.controller.admin;

import com.example.library.DTO.BookDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.BookCount;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookCountRepository;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookCountRepository bookCountRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, BookCountRepository bookCountRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookCountRepository= bookCountRepository;
    }

    @GetMapping("/showAddBookForm")
    public String showAddBookForm(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        model.addAttribute("allAuthors", authorRepository.findAll());
        return "admin/add-book";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute("bookDTO") BookDTO bookDTO, @RequestParam("authors") List<Long> authorIds) {
        List<Author> selectedAuthors = authorRepository.findAllById(authorIds);
        bookDTO.setAuthors(selectedAuthors);
        Book book = BookDTO.mapToBook(bookDTO);
        bookRepository.save(book);

        BookCount bookCount = new BookCount();
        bookCount.setBook(book);
        bookCount.setCount(bookDTO.getBookCount());
        bookCountRepository.save(bookCount);

        return "redirect:/book/showAddBookForm";
    }
}