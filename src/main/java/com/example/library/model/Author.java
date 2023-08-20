package com.example.library.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "this field cannot be void")
    @NotBlank(message = "this field cannot be void")
    private String firstName;

    @NotNull(message = "this field cannot be void")
    @NotBlank(message = "this field cannot be void")
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    @ToString.Exclude
    private List<Book> books;
}
