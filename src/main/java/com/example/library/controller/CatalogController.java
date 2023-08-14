package com.example.library.controller.catalog;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.service.implementation.AuthorService;
import com.example.library.service.implementation.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public CatalogController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/authorInfo/{id}")
    public String showAuthorInfo(@PathVariable Long id, Model model) {
        Author author = authorService.findAuthorById(id);
        model.addAttribute("author", author);
        return "admin/author-info";
    }

    @GetMapping("/bookInfo/{id}")
    public String showBookInfo(@PathVariable Long id, Model model) {
        Book book = bookService.findBookById(id);
        model.addAttribute("book", book);
        return "admin/book-info";
    }

    @GetMapping("/showBookCatalog")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "catalog/book-catalog";
    }
}
