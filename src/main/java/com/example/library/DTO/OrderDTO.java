package com.example.library.DTO;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OrderDTO {
    private String bookTitle;
    private Date orderDate;
    private String returnDate;
    private Reader reader;
    private Book book;
    private boolean isReturned;

    public OrderDTO(String returnDate){
        this.returnDate = returnDate;
    }
}
