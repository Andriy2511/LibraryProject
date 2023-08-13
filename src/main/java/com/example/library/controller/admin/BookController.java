package com.example.library.controller.admin;

import com.example.library.DTO.BookDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.BookCount;
import com.example.library.model.Reader;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookCountRepository;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/showBookList")
    public String showBookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "admin/book-list";
    }

    @GetMapping("/showAddBookForm")
    public String showAddBookForm(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        model.addAttribute("allAuthors", authorRepository.findAll());
        return "admin/add-book";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute("bookDTO") BookDTO bookDTO, @RequestParam("authors") List<Long> authorId) {
        List<Author> selectedAuthors = authorRepository.findAllById(authorId);
        bookDTO.setAuthors(selectedAuthors);
        Book book = BookDTO.mapToBook(bookDTO);
        bookRepository.save(book);

        BookCount bookCount = new BookCount();
        bookCount.setBook(book);
        bookCount.setCount(bookDTO.getBookCount());
        bookCountRepository.save(bookCount);

        return "redirect:/book/showAddBookForm";
    }

    @GetMapping("/bookInfo/{id}")
    public String showUserInfo(@PathVariable Long id, Model model) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            model.addAttribute("book", bookOptional.get());
        } else {
            model.addAttribute("book", null);
        }
        return "admin/book-info";
    }

    @GetMapping("/showBookCatalog")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "catalog/book-catalog";
    }
}