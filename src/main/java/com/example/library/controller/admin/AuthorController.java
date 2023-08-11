package com.example.library.controller.admin;

import com.example.library.DTO.BookDTO;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.BookCount;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/showAddAuthorForm")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "admin/add-author";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@ModelAttribute("author") Author author) {
        authorRepository.save(author);
        return "redirect:/author/showAddAuthorForm";
    }
}
