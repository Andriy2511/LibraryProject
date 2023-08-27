package com.example.library.controller;

import com.example.library.DTO.BookDTO;
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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getBooksWithPaginationAndSorting(Model model, @AuthenticationPrincipal Reader reader, HttpSession session,
                                                   @RequestParam(name = "sortingField", required = false) String sortingField,
                                                   @RequestParam(name = "page",  required = false) Integer page,
                                                   @RequestParam(name = "pageSize", required = false) Integer recordPerPage) {
        boolean isAdmin = roleService.isUserContainRole(reader, "ADMIN");

        configurePaginationAndSorting(sortingField, page, recordPerPage);

        List<Book> bookList = bookService.
                findBooksWithPaginationAndSorting(paginationData.getPage(), paginationData.getPageSize(), paginationData.getSortingField())
                .get().toList();
        List<BookDTO> bookDTOList = new ArrayList<>();

        setAvailableBookToBookDTOList(bookList, bookDTOList);

        model.addAttribute("bookDTOList", bookDTOList);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("paginationData", paginationData);

        processNotAvailableBooks(session, model);

        log.info("Reader: {}; Is reader admin: {}", reader, isAdmin);
        return "catalog/book-catalog";
    }

    private void configurePaginationAndSorting(String sortingField, Integer page, Integer recordPerPage) {
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
    }

    private void setAvailableBookToBookDTOList(List<Book> bookList, List<BookDTO> bookDTOList){
        for (Book book : bookList) {
            BookDTO bookDTO = BookDTO.mapToBookDTO(book);
            bookDTO.setAvailableBook(bookService.getCountOfAvailableBooks(book));
            bookDTOList.add(bookDTO);
        }
    }

    private void processNotAvailableBooks(HttpSession session, Model model) {
        if (session.getAttribute("notAvailableBook") != null) {
            model.addAttribute("notAvailableBooks", session.getAttribute("notAvailableBook"));
            session.removeAttribute("notAvailableBook");
        }
    }

    private Integer countTotalPages(Integer recordPerPage){
        Long totalRecords = bookService.selectCountOfBooks();
        return (int) Math.ceil((double) totalRecords/recordPerPage);
    }
}
