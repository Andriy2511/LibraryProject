package com.example.library.model;

import lombok.*;

import javax.persistence.*;

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
    private Book book;

    private Integer count;
}
