package io.ermdev.alice.controller;

import io.ermdev.alice.entity.User;
import io.ermdev.alice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{userId}")
    public User getById(@PathVariable("userId") Long userId) {
        return userRepository.findById(userId);
    }

    @GetMapping("all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public User add(@RequestBody User user) {
        return userRepository.save(user);
    }
}
