package com.sendingEmails.SendingEmails.service;

import com.sendingEmails.SendingEmails.entity.User;
import com.sendingEmails.SendingEmails.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findNextDayAppointments (){
        LocalDate nextDay = LocalDate.now().plusDays(1);
        return userRepository.findAllByAppointmentNextDay(nextDay);
    }

    public User addUser(User user) {
        if (user.getName() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("Name and email are required fields.");
        }
        return userRepository.save(user);
    }
    public List<User> findUsersByName(String name) {
        return userRepository.findByName(name);
    }
}
