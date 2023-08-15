package com.example.library.controller;

import com.example.library.DTO.ReaderDTO;
import com.example.library.model.Reader;
import com.example.library.service.IReaderService;
import com.example.library.service.IRoleService;
import com.example.library.service.implementation.ReaderService;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegistrationController {

    private final IReaderService readerService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(ReaderService readerService, IRoleService roleService, PasswordEncoder passwordEncoder){
        this.readerService = readerService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * The method adds the readerDTO attribute of the ReaderDTO class
     * @param model A Model class object
     * @return registration.html page
     */
    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("readerDTO", new ReaderDTO());
        return "registration";
    }

    /**
     * This method is responsible for adding users to the database
     * @param readerDTO An object of the ReaderDTO class
     * @param result An object of the BindingResult class
     * @return If the user has been registered successfully, the method returns the login page, in other case, it returns the registration page
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute ("readerDTO") @Valid ReaderDTO readerDTO, BindingResult result){
        if(result.hasErrors()){
            return "registration";
        }

        try {
            if (readerService.isUserExistCheckByUsername(readerDTO.getUsername())) {
                log.info("A user with this name has already exists, username: {}", readerDTO.getUsername());
                result.rejectValue("username", "error.duplicateUsername", "Користувач з таким іменем вже існує");
                return "registration";
            }
            if (readerService.isUserExistCheckByEmail(readerDTO.getEmail())) {
                log.info("A user with this email has already exists, email: {}", readerDTO.getEmail());
                result.rejectValue("email", "error.duplicateEmail", "Така електронна пошта вже зареєстрована");
                return "registration";
            }

            readerService.registerUser(getReaderFromDTO(readerDTO));
            return "redirect:login";
        } catch (Exception e){
            log.error("Error while adding to the database: \n{}", e.getMessage());
            result.rejectValue( "database.addingReaderError", "Помилка під час реєстрації: " + e.getMessage());
            return "registration";
        }
    }

    private Reader getReaderFromDTO(ReaderDTO readerDTO){
        Reader reader = new Reader();
        reader.setUsername(readerDTO.getUsername());
        reader.setPassword(passwordEncoder.encode(readerDTO.getPassword()));
        reader.setEmail(readerDTO.getEmail());
        reader.setBlocked(false);
        reader.setRoles(roleService.findRoleByName("USER"));
        return reader;
    }
}
