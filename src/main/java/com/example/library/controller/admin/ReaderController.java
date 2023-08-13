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

/**
 * Controller class responsible for handling HTTP requests related to managing readers (users) in the library system.
 */
@Controller
@RequestMapping("/reader")
public class ReaderController {

    private final ReaderRepository readerRepository;
    private final RoleRepository roleRepository;

    /**
     * Constructor for the ReaderController class.
     *
     * @param readerRepository The repository for accessing reader (user) data.
     * @param roleRepository   The repository for accessing role data.
     */
    @Autowired
    public ReaderController(ReaderRepository readerRepository, RoleRepository roleRepository) {
        this.readerRepository = readerRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Handles HTTP POST requests to block or unblock a user's account.
     *
     * @param id The ID of the reader whose account should be blocked or unblocked.
     * @return A redirect to the user list page after blocking/unblocking.
     */
    @PostMapping("/blockUser/{id}")
    public String blockUser(@PathVariable Long id) {
        Reader reader = readerRepository.findById(id).orElse(null);
        if (reader != null) {
            reader.setBlocked(!reader.isBlocked());
            readerRepository.save(reader);
        }
        return "redirect:/user/showUserList";
    }

    /**
     * Handles HTTP GET requests to display a list of readers (users).
     *
     * @param model The Spring Model object used to convey data to the view.
     * @return The view name "admin/user-list" to display the list of readers.
     */
    @GetMapping("/showUserList")
    public String showBookList(Model model) {
        List<Role> roleList = roleRepository.findAllByName("USER");
        model.addAttribute("readers", readerRepository.findAllByRolesIn(roleList));
        return "admin/user-list";
    }

    /**
     * Handles HTTP GET requests to display detailed information about a specific reader (user).
     *
     * @param id    The ID of the reader whose information should be displayed.
     * @param model The Spring Model object used to convey data to the view.
     * @return The view name "admin/user-info" to display detailed user information.
     */
    @GetMapping("/userInfo/{id}")
    public String showUserInfo(@PathVariable Long id, Model model) {
        Optional<Reader> userOptional = readerRepository.findById(id);

        if (userOptional.isPresent()) {
            model.addAttribute("reader", userOptional.get());
        } else {
            model.addAttribute("reader", null);
        }
        return "admin/user-info";
    }
}