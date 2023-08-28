package com.example.library.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    private Date orderDate;
    private Date returnDate;
    private boolean isReturned;

    @OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private Fine fine;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", returnDate=" + returnDate +
                ", isReturned=" + isReturned +
                ", username=" + reader.getUsername() +
                ", book=" + book.getTitle() +
                '}';
    }
}
