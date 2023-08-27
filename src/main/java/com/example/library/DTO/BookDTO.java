package com.example.library.DTO;

import com.example.library.model.Author;
import com.example.library.model.Book;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
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

    private Long id;
    @NotBlank(message = "The name cannot be empty")
    @NotNull(message = "The name cannot be empty")
    @Size(min = 5, message = "Name must have at least 5 letters")
    private String title;

    @NotNull(message = "The book must have an author")
    private List<Author> authors;

    private MultipartFile photo;
    private String photoName;
    @NotBlank(message = "The book must have a description")
    private String description;

    private String publicationDate;

    @NotNull(message = "Count of book cannot be void or negative")
    @Min(value = 0, message = "Count of book cannot be void or negative")
    private Integer bookCount;

    private Integer availableBook;

    public BookDTO(Long id, String title, MultipartFile photo, String publicationDate, String description, List<Author> authors,
                   Integer bookCount, Integer availableBook, String photoName){
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.publicationDate = publicationDate;
        this.description = description;
        this.authors = authors;
        this.bookCount = bookCount;
        this.availableBook = availableBook;
        this.photoName = photoName;
    }

    public static Book mapToBook(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setPhoto(bookDTO.getPhoto() != null && !bookDTO.getPhoto().isEmpty() ? bookDTO.getPhoto().getOriginalFilename() : "without photo");
        book.setPublicationDate(mapToDate(bookDTO.getPublicationDate()));
        book.setAuthors(bookDTO.getAuthors());
        return book;
    }

    public static BookDTO mapToBookDTO(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthors(book.getAuthors());
        bookDTO.setPhotoName(book.getPhoto());
        bookDTO.setDescription(book.getDescription());
        if(book.getPublicationDate() != null)
            bookDTO.setPublicationDate(book.getPublicationDate().toString());
        return bookDTO;
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
