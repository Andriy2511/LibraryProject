package com.example.library.controller;

import com.example.library.component.CatalogPaginationData;
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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalog")
@Slf4j
public class CatalogController {

    private final IAuthorService authorService;
    private final IBookService bookService;
    private final IRoleService roleService;
    private final CatalogPaginationData paginationData;

    @Autowired
    public CatalogController(IAuthorService authorService, IBookService bookService, IRoleService roleService, CatalogPaginationData paginationData) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.roleService = roleService;
        this.paginationData = paginationData;
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
    public String getBooksWithPaginationAndSorting(Model model, @AuthenticationPrincipal Reader reader,
                                                   @RequestParam(name = "sortingField", required = false) String sortingField,
                                                   @RequestParam(name = "page",  required = false) Integer page,
                                                   @RequestParam(name = "pageSize", required = false) Integer recordPerPage) {
        boolean isAdmin = roleService.isUserContainRole(reader, "ADMIN");

        if (sortingField != null) {
            paginationData.setSortingField(sortingField);
        }
        if (page != null && page >= 0) {
            paginationData.setPage(page);
        }
        if (recordPerPage != null && recordPerPage > 0) {
            paginationData.setPageSize(recordPerPage);
            paginationData.setTotalPages(countTotalPages(recordPerPage));
        }


        model.addAttribute("books", bookService.
                findBooksWithPaginationAndSorting(paginationData.getPage(), paginationData.getPageSize(), paginationData.getSortingField()));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("paginationData", paginationData);
        log.info("Reader: {}; Is reader admin: {}", reader, isAdmin);
        return "catalog/book-catalog";
    }

    private Integer countTotalPages(Integer recordPerPage){
        Long totalRecords = bookService.selectCountOfBooks();
        return (int) Math.ceil((double) totalRecords/recordPerPage);
    }
}
