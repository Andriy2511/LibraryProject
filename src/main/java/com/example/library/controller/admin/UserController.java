package com.example.library.controller.admin;

import com.example.library.model.Reader;
import com.example.library.model.Role;
import com.example.library.repository.ReaderRepository;
import com.example.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/blockUser/{id}")
    public String blockUser(@PathVariable Long id) {
        Reader reader = readerRepository.findById(id).orElse(null);
        if (reader != null) {
            reader.setBlocked(!reader.isBlocked());
            readerRepository.save(reader);
        }
        return "redirect:/user/showUserList";
    }

    @GetMapping("/showUserList")
    public String showBookList(Model model) {
        List<Role> roleList = roleRepository.findAllByName("USER");
        model.addAttribute("users", readerRepository.findAllByRolesIn(roleList));
        return "admin/user-list";
    }

    @GetMapping("/userInfo/{id}")
    public String showUserInfo(@PathVariable Long id, Model model) {
        Optional<Reader> userOptional = readerRepository.findById(id);

        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        } else {
            model.addAttribute("user", null);
        }
        return "admin/user-info";
    }
}