package com.example.library.DTO;

import com.example.library.model.Reader;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO {
    @NotBlank(message = "Ім'я користувача не може бути порожнім")
    private String username;
    @Size(min = 6, message = "Пароль повинен містити щонайменше 6 символів")
    private String password;
    @Email(message = "Введіть коректну адресу електронної пошти")
    private String email;

    public ReaderDTO(Reader reader){
        this.username = reader.getUsername();
        this.password = reader.getPassword();
        this.email = reader.getEmail();
    }
}
