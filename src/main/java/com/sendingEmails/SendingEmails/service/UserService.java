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

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findNextDayAppointments() {
        LocalDate nextDay = LocalDate.now().plusDays(1);
        return userRepository.findAllByAppointmentNextDay(nextDay);
    }

    public List<User> findTodayAppointments() {
        LocalDate today = LocalDate.now();
        return userRepository.findAllByAppointmentNextDay(today);
    }

    public User addUser(User user) {
        validateUser(user); // Ensure all required fields are provided
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            throw new NoSuchElementException("Cannot update user; user does not exist.");
        }
        validateUser(user);
        return userRepository.save(user);
    }

    public boolean deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Cannot delete user; user with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
        return true;
    }

    public List<User> findUsersByName(String name) {
        return userRepository.findByName(name);
    }

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

    public List<User> getUsersWithOldAppointments() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);  // Get the date one week ago
        return userRepository.findUsersWithOldAppointments(oneWeekAgo);
    }

    public List<User> getUsersByAppointmentDate (LocalDate appointment){
        return userRepository.findAllByAppointmentDate(appointment);
    }

}
