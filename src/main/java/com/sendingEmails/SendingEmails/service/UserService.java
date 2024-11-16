package com.sendingEmails.SendingEmails.service;

import com.sendingEmails.SendingEmails.entity.User;
import com.sendingEmails.SendingEmails.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Retrieve user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    // Retrieve user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find users with appointments scheduled for the next day
    public List<User> findNextDayAppointments() {
        LocalDate nextDay = LocalDate.now().plusDays(1);
        return userRepository.findAllByAppointmentNextDay(nextDay);
    }

    // Add a new user
    public User addUser(User user) {
        validateUser(user); // Ensure all required fields are provided
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            throw new NoSuchElementException("Cannot update user; user does not exist.");
        }
        validateUser(user);
        return userRepository.save(user);
    }

    // Delete a user by ID
    public boolean deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Cannot delete user; user with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
        return true;
    }

    // Search for users by name
    public List<User> findUsersByName(String name) {
        return userRepository.findByName(name);
    }

    // Helper method to validate user fields
    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name is required.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email is required.");
        }
        if (user.getAppointment() == null) {
            throw new IllegalArgumentException("Appointment date is required.");
        }
    }
}
