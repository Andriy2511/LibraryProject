package com.example.library.DTO;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.util.Date;

@Getter
@Setter
@ToString
@Slf4j
public class OrderDTO {
    private String bookTitle;
    private Date orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "ou cannot set present or past date for your order")
    private Date returnDate;
    private Reader reader;
    private Book book;
    private boolean isReturned;

    public OrderDTO(Date returnDate) {
        this.returnDate = returnDate;
    }
}
