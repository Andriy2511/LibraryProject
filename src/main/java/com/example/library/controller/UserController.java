package com.example.library.controller;

import com.example.library.model.Order;
import com.example.library.model.Reader;
import com.example.library.service.IBookService;
import com.example.library.service.IOrderService;
import com.example.library.service.IReaderService;
import com.example.library.service.implementation.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final IReaderService readerService;
    private final IOrderService orderService;
    private final IBookService bookService;

    @Autowired
    public UserController(ReaderService readerService, IOrderService orderService, IBookService bookService) {
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

    @PostMapping("/confirmOrder/{bookId}")
    public String confirmNewOrder(@PathVariable Long bookId, @AuthenticationPrincipal Reader reader,
                                  @SessionAttribute("unconfirmedOrders") List<Order> unconfirmedOrders) {
        removeOrderFromListByBookId(unconfirmedOrders, bookId);
        Order order = new Order();
        order.setReader(reader);
        order.setBook(bookService.findBookById(bookId));
        order.setReturned(false);
        orderService.saveOrder(order);
        return "redirect:/user/showOrderConfirmationPage";
    }

    @PostMapping("/cancelOrder/{bookId}")
    public String cancelOrder(@PathVariable Long bookId, @SessionAttribute("unconfirmedOrders") List<Order> unconfirmedOrders) {
        removeOrderFromListByBookId(unconfirmedOrders, bookId);
        log.info("List after canceling order: {}", unconfirmedOrders);
        return "redirect:/user/showOrderConfirmationPage";
    }

    @GetMapping("/showOrderConfirmationPage")
    public String showConfirmOrdersPage(Model model, @SessionAttribute("unconfirmedOrders") List<Order> unconfirmedOrders) {
        log.info("Unconfirmed Orders list is: {}", unconfirmedOrders);
        model.addAttribute("unconfirmedOrders", unconfirmedOrders);
        return "reader/order-confirmation-page";
    }

    @PostMapping("/addOrder/{bookId}")
    public String addNewOrder(@SessionAttribute("unconfirmedOrders") List<Order> unconfirmedOrders, @PathVariable Long bookId, @AuthenticationPrincipal Reader reader) {
        Order order = new Order();
        order.setReader(reader);
        order.setBook(bookService.findBookById(bookId));
        order.setReturned(false);
        unconfirmedOrders.add(order);
        return "redirect:/catalog/showBookCatalog";
    }

    private void removeOrderFromListByBookId(List<Order> orders, Long bookId){
        for (Order order : orders) {
            if(order.getBook().getId().equals(bookId)){
                orders.remove(order);
                break;
            }
        }
    }
}
