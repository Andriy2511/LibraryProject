package com.example.library.controller.admin;

import com.example.library.model.Role;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.ReaderRepository;
import com.example.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final ReaderRepository readerRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(ReaderRepository readerRepository, RoleRepository roleRepository) {
        this.readerRepository = readerRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/showUserList")
    public String showBookList(Model model) {
        List<Role> roleList = roleRepository.findAllByName("USER");
        model.addAttribute("users", readerRepository.findAllByRolesIn(roleList));
        return "admin/user-list";
    }
}
