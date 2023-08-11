package com.example.library.DTO;

import com.example.library.model.Author;
import com.example.library.model.Book;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class BookDTO {
    @NotBlank(message = "The name cannot be empty")
    @Size(min = 3, message = "Name must have at least 3 letters")
    private String title;

    @NotNull(message = "The book must have an author")
    @ToString.Exclude
    private List<Author> authors;
    private String photo;
    @NotBlank(message = "The book must have a description")
    private String description;

    private String publicationDate;

    private Integer bookCount;

    public BookDTO(String title, String photo, String publicationDate, String description, List<Author> authors, Integer bookCount){
        this.title = title;
        this.photo = photo;
        this.publicationDate = publicationDate;
        this.description = description;
        this.authors = authors;
        this.bookCount = bookCount;
    }

    public static Book mapToBook(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setPhoto(bookDTO.getPhoto());
        book.setPublicationDate(mapToDate(bookDTO.getPublicationDate()));
        book.setAuthors(bookDTO.getAuthors());
        return book;
    }

    private static Date mapToDate(String publicationDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.parse(publicationDate);
        } catch (ParseException e){
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }
}
