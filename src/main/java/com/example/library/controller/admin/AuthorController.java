package com.example.library.controller.admin;

import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/showAuthorList")
    public String showBookList(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "admin/author-list";
    }
}
