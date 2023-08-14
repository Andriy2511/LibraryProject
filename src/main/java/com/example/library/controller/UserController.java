package com.example.library.controller;

import com.example.library.model.Order;
import com.example.library.model.Reader;
import com.example.library.service.IBookService;
import com.example.library.service.IOrderService;
import com.example.library.service.implementation.BookService;
import com.example.library.service.implementation.OrderService;
import com.example.library.service.implementation.IReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final IReaderService readerService;
    private final IOrderService orderService;
    private final IBookService bookService;


    @Autowired
    public UserController(IReaderService readerService, IOrderService orderService, IBookService bookService) {
        this.readerService = readerService;
        this.orderService = orderService;
        this.bookService = bookService;
    }


    @GetMapping("/showReaderOrderList")
    public String showOrderByReader(Model model, Principal principal) {
        Reader reader = readerService.findReaderByName(principal.getName());
        model.addAttribute("orders", orderService.findOrdersByReader(reader));
        return "reader/reader-order-list";
    }

    @PostMapping("/addOrder/{bookId}")
    public String addNewOrder(@PathVariable Long bookId, @AuthenticationPrincipal Reader reader) {
        Order order = new Order();
        order.setReader(reader);
        order.setBook(bookService.findBookById(bookId));
        order.setReturned(false);
        orderService.saveOrder(order);

        return "redirect:/catalog/showBookCatalog";
    }
}
