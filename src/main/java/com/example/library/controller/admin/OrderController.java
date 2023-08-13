package com.example.library.controller.admin;

import com.example.library.model.*;
import com.example.library.repository.BookRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final ReaderService readerService;
    private final BookRepository bookRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ReaderService readerService, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.readerService = readerService;
        this.bookRepository = bookRepository;
    }

    /**
     * Handles HTTP GET requests to "/order/showOrderList" URL path and then displaying a list of orders.
     *
     * @param model The Spring Model object used to convey data to the view.
     * @return The view name "admin/order-list" to display the list of orders.
     */
    @GetMapping("/showOrderList")
    public String showOrderList(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/order-list";
    }

    @GetMapping("/showReaderOrderList")
    public String showOrderByReader(Model model, Principal principal) {
        Reader reader = readerService.findReaderByName(principal.getName());
        model.addAttribute("orders", orderRepository.findAllByReader(reader));
        return "reader/reader-order-list";
    }

    @PostMapping("/addOrder/{bookId}")
    public String addNewOrder(@PathVariable Long bookId, @AuthenticationPrincipal Reader reader) {
        Order order = new Order();
        order.setReader(reader);
        order.setBook(bookRepository.findById(bookId).get());
        order.setReturned(false);
        orderRepository.save(order);

        return "redirect:/book/showBookCatalog";
    }
}
