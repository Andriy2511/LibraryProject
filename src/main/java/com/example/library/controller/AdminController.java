package com.example.library.controller;

import com.example.library.DTO.BookDTO;
import com.example.library.component.ListPaginationData;
import com.example.library.model.*;
import com.example.library.service.*;
import com.example.library.service.implementation.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final IReaderService readerService;
    private final IAuthorService authorService;
    private final IBookService bookService;
    private final IBookCountService bookCountService;
    private final IOrderService orderService;
    private final IRoleService roleService;
    private final ListPaginationData listPaginationData;

    @Autowired
    public AdminController(ReaderService readerService, IAuthorService authorService, IBookService bookService,
                           IBookCountService bookCountService, IOrderService orderService, IRoleService roleService, ListPaginationData listPaginationData) {
        this.readerService = readerService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bookCountService = bookCountService;
        this.orderService = orderService;
        this.roleService = roleService;
        this.listPaginationData = listPaginationData;
    }

    @GetMapping("/showAddAuthorForm")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "admin/add-author";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@ModelAttribute("author") @Valid Author author, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/add-author";
        }
        authorService.addNewAuthor(author);
        return "redirect:/admin/showAddAuthorForm";
    }

    @GetMapping("/showAuthorList")
    public String showAuthorList(Model model) {
        listPaginationData.setTotalRecords(authorService.getAuthorsCount());
        model.addAttribute("authors", authorService.findAllAuthorsWithPagination(listPaginationData.getPage(), listPaginationData.getPageSize()));
        return "admin/author-list";
    }

    @GetMapping("/showBookList")
    public String showBookList(Model model) {
        listPaginationData.setTotalRecords(bookService.getBooksCount());
        model.addAttribute("books", bookService.findAllBooksWithPagination(listPaginationData.getPage(), listPaginationData.getPageSize()));
        return "admin/book-list";
    }

    @GetMapping("/showAddBookForm")
    public String showAddBookForm(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        model.addAttribute("allAuthors", authorService.findAllAuthors());
        return "admin/add-book";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute("bookDTO") @Valid BookDTO bookDTO,
                          BindingResult bindingResult,
                          @RequestParam(value = "authors", required = false) List<Long> authorsId,
                          Model model) {

        if (authorsId == null || authorsId.isEmpty()) {
            bindingResult.rejectValue("authors", "error.authors", "Select at least one author");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allAuthors", authorService.findAllAuthors());
            if(bindingResult.hasFieldErrors("authors")) {
                model.addAttribute("incorrectAuthors", "Select at least one author");
            }
            return "/admin/add-book";
        }

        List<Author> selectedAuthors = authorService.findAllAuthorsById(authorsId);
        bookDTO.setAuthors(selectedAuthors);

        addPhotoToFolder(bookDTO.getPhoto());

        Book book = BookDTO.mapToBook(bookDTO);
        bookService.addBook(book);

        BookCount bookCount = new BookCount();
        bookCount.setBook(book);
        bookCount.setCount(bookDTO.getBookCount());
        bookCountService.saveBookCount(bookCount);

        return "redirect:/admin/showAddBookForm";
    }

    /**
     * Handles HTTP GET requests to "/order/showOrderList" URL path and then displaying a list of orders.
     *
     * @param model The Spring Model object used to convey data to the view.
     * @return The view name "admin/order-list" to display the list of orders.
     */
    @GetMapping("/showOrderList")
    public String showOrderList(Model model) {
        listPaginationData.setTotalRecords(orderService.getOrdersCount());
        model.addAttribute("orders", orderService.showAllOrdersWithPagination(listPaginationData.getPage(), listPaginationData.getPageSize()));
        return "admin/order-list";
    }

    /**
     * Handles HTTP POST requests to block or unblock a user's account.
     *
     * @param id The ID of the reader whose account should be blocked or unblocked.
     * @return A redirect to the user list page after blocking/unblocking.
     */
    @PostMapping("/blockUser/{id}")
    public String blockUser(@PathVariable Long id) {
        Reader reader = readerService.findReaderById(id);
        if (reader != null) {
            reader.setBlocked(!reader.isBlocked());
            readerService.saveReader(reader);
        }
        return "redirect:/admin/showUserList";
    }

    /**
     * Handles HTTP GET requests to display a list of readers (users).
     *
     * @param model The Spring Model object used to convey data to the view.
     * @return The view name "admin/user-list" to display the list of readers.
     */
    @GetMapping("/showUserList")
    public String showUserList(Model model) {
        List<Role> roleList = roleService.findAllRoleByName("USER");
        listPaginationData.setTotalRecords(readerService.getCountReadersByRole(roleList.get(0)));
        model.addAttribute("readers", readerService.findAllReadersByRolesWithPagination(roleList,
                listPaginationData.getPage(), listPaginationData.getPageSize()));
        return "admin/user-list";
    }

    /**
     * Handles HTTP GET requests to display detailed information about a specific reader (user).
     *
     * @param id    The ID of the reader whose information should be displayed.
     * @param model The Spring Model object used to convey data to the view.
     * @return The view name "admin/user-info" to display detailed user information.
     */
    @GetMapping("/userInfo/{id}")
    public String showUserInfo(@PathVariable Long id, Model model) {
        Reader reader = readerService.findReaderById(id);
        log.info(String.valueOf(reader));
        model.addAttribute("reader", reader);
        return "admin/user-info";
    }

    /**
     * The method saves photo for book to the "resources/static/bookphoto/" folder
     * @param photoFile A photo which has to be stored
     */
    private void addPhotoToFolder(MultipartFile photoFile){
        if (photoFile != null && !photoFile.isEmpty()) {
            try {
                String fileName = photoFile.getOriginalFilename();
                Path filePath = Paths.get("src/main/resources/static/bookphoto/" + fileName);
                photoFile.transferTo(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
