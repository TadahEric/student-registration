package com.mt.springmongo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller which is responsible for retrieving {@link User}s.
 *
 * @author Mithun
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class); // Fixed this line
    
    @Autowired
    public UserResource(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(produces = "application/json")
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll(); // Remove the cast
        logger.info("Get Users Total Users: " + users.size());
        return users;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<User> findById(@PathVariable("id") String userID) {
        logger.info("Get User By Id: " + userID);
        return userRepository.findById(userID);
    }
    
    // Optional: Add a POST endpoint for AJAX submission
    @PostMapping(produces = "application/json")
    public User createUser(@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("email") String email) {
        logger.info("Creating user: {} {}", firstName, lastName);
        User user = new User(firstName, lastName, email);
        return userRepository.save(user);
    }
}