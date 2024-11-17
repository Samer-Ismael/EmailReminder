package com.sendingEmails.SendingEmails.config;

import com.sendingEmails.SendingEmails.entity.User;
import com.sendingEmails.SendingEmails.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CleaningDatabaseSchedule {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CleaningDatabaseSchedule.class);

    @Autowired
    public CleaningDatabaseSchedule(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Scheduled method to run daily at 1:00 PM
    @Scheduled(cron = "0 0 13 * * ?")
    public void cleanOldAppointments() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        List<User> usersToDelete = userRepository.findUsersWithOldAppointments(oneWeekAgo);

        if (!usersToDelete.isEmpty()) {
            for (User user : usersToDelete) {
                long id = user.getId();
                userRepository.deleteById(id);
            }
            logger.error("Database is clean");

        } else {
            logger.error("No users to delete");
        }
    }
}