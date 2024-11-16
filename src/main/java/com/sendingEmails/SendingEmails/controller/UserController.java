package com.sendingEmails.SendingEmails.controller;

import com.sendingEmails.SendingEmails.entity.User;
import com.sendingEmails.SendingEmails.service.EmailService;
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
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        emailService.sendWelcomeEmail(user); // Send welcome email
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.noContent().build();
        }

        // Update fields
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAppointment(updatedUser.getAppointment());
        existingUser.setTime(updatedUser.getTime());

        User savedUser = userService.updateUser(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.deleteUserById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> findUsersByName(@RequestParam String name) {
        List<User> users = userService.findUsersByName(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/next-day-appointments")
    public ResponseEntity<List<User>> getNextDayAppointments() {
        List<User> users = userService.findNextDayAppointments();
        return ResponseEntity.ok(users);
    }

}
