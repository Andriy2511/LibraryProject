package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.service.IAuthorService;
import com.example.library.service.IBookService;
import com.example.library.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/catalog")
@SessionAttributes("unconfirmedOrders")
@Slf4j
public class CatalogController {

    private final IAuthorService authorService;
    private final IBookService bookService;
    private final IRoleService roleService;

    @Autowired
    public CatalogController(IAuthorService authorService, IBookService bookService, IRoleService roleService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.roleService = roleService;
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
    public String getAllBooks(Model model, @AuthenticationPrincipal Reader reader) {
        boolean isAdmin = roleService.isUserContainRole(reader, "ADMIN");
        model.addAttribute("books", bookService.findAllBooks());
        model.addAttribute("isAdmin", isAdmin);
        log.info("Reader: {}; Is reader admin: {}", reader, isAdmin);
        return "catalog/book-catalog";
    }
}
