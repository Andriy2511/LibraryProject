package com.example.library.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books_count")
public class BookCount {

    @Id
    @Column(name = "book_id")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private Book book;

    @Min(value = 0, message = "Book count cannot be less then 0")
    private Integer count;
}
