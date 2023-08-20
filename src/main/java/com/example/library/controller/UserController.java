package com.example.library.controller;

import com.example.library.DTO.BookDTO;
import com.example.library.DTO.OrderDTO;
import com.example.library.DTO.ReaderDTO;
import com.example.library.model.Order;
import com.example.library.model.Reader;
import com.example.library.service.IBookService;
import com.example.library.service.IOrderService;
import com.example.library.service.IReaderService;
import com.example.library.service.implementation.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    /**
     * Confirms a new order and adds it to the database.
     * If the return date isn't after tomorrow,
     * the order-confirmation-page is displayed with an appropriate message.
     *
     * @param bookId The ID of the book for which the order is being confirmed.
     * @param orderDTO The data transfer object containing order details, including the return date.
     * @param reader The currently authenticated reader performing the order confirmation.
     * @param unconfirmedOrders The list of unconfirmed orders stored in the session.
     * @param model The Spring MVC model for rendering views and attributes.
     * @return A view name to redirect after the order confirmation process.
     */
    @PostMapping("/confirmOrder/{bookId}")
    public String confirmNewOrder(
            @PathVariable Long bookId,
            @ModelAttribute("orderDTO") @Valid OrderDTO orderDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal Reader reader,
            @SessionAttribute("unconfirmedOrders") List<Order> unconfirmedOrders,
            Model model) {

        System.out.println("In class UserController"  + bindingResult.hasErrors());

        if (!orderDTO.getReturnDate().after(new Date())) {
            model.addAttribute("unconfirmedOrders", unconfirmedOrders);
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            model.addAttribute("orderDTO", new OrderDTO(Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant())));
            model.addAttribute("incorrectDate", "You cannot set present or past date for your order");
            return "reader/order-confirmation-page";
        }
        
        removeOrderFromListByBookId(unconfirmedOrders, bookId);

        Order order = new Order();
        order.setReader(reader);
        order.setBook(bookService.findBookById(bookId));
        order.setReturned(false);
        order.setOrderDate(getCurrentDate());
        order.setReturnDate(orderDTO.getReturnDate());
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
        model.addAttribute("orderDTO", new OrderDTO(new Date()));
        return "reader/order-confirmation-page";
    }

    @PostMapping("/addOrder/{bookId}")
    public String addNewOrder(@SessionAttribute("unconfirmedOrders") List<Order> unconfirmedOrders,
                              @PathVariable Long bookId, @AuthenticationPrincipal Reader reader) {
        Order order = new Order();
        order.setReader(reader);
        order.setBook(bookService.findBookById(bookId));
        order.setReturned(false);
        order.setOrderDate(getCurrentDate());
        order.setReturnDate(getCurrentDate());
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

    private Date getCurrentDate(){
        LocalDate currentDate = LocalDate.now();
        return java.sql.Date.valueOf(currentDate);
    }
}
