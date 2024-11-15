package com.sendingEmails.SendingEmails.controller;

import com.sendingEmails.SendingEmails.entity.User;
import com.sendingEmails.SendingEmails.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/next-day-appointments")
    public ResponseEntity<List<User>> getNextDayAppointments() {
        List<User> users = userService.findNextDayAppointments();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>> findUsersByName(@RequestParam String name) {
        List<User> users = userService.findUsersByName(name);
        return ResponseEntity.ok(users);
    }
}
