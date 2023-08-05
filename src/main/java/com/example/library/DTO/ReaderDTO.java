package com.example.library.DTO;

import com.example.library.model.Reader;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
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
