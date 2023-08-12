package com.example.library.controller.admin;

import com.example.library.model.Author;
import com.example.library.model.Reader;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/authorInfo/{id}")
    public String showAuthorInfo(@PathVariable Long id, Model model) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            model.addAttribute("author", authorOptional.get());
        } else {
            model.addAttribute("author", null);
        }
        return "admin/author-info";
    }
}
