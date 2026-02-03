package com.mt.springmongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Controller which handles request for saving {@link User}s.
 *
 * @author LandmarkTech
 */
@Controller
public class UserController {
    private final UserRepository userRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Render the main page
    @GetMapping("/")
    public String index(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    // Handle form submission
    @PostMapping(value = "/save")
    public String save(@RequestParam("firstName") String firstName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("email") String email,
                       @RequestParam("age") Integer age) {

        logger.info("Creating user name: " + firstName);
        User user = new User(firstName, lastName, email, age);
        userRepository.save(user);

        return "redirect:/";
    }

    // Add this API endpoint for AJAX calls
    @GetMapping("/api/users")
    @ResponseBody
    public List<User> getUsers() {
        logger.info("Fetching all users via API");
        return userRepository.findAll();
    }
}