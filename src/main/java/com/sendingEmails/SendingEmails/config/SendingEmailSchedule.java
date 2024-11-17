package com.sendingEmails.SendingEmails.config;

import com.sendingEmails.SendingEmails.entity.User;
import com.sendingEmails.SendingEmails.service.UserService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SendingEmailSchedule {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SendingEmailSchedule.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired

    public SendingEmailSchedule(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 40 14 * * ?") // for testing the app.
    //@Scheduled(cron = "0 0 9 * * ?")  // Runs every day at 9:00 AM
    public void findUsersWithNextDayAppointments() {
        logger.info("Scheduled task triggered at {}", LocalDateTime.now());
        List<User> users = userService.findNextDayAppointments();

        if (users.isEmpty()) {
            logger.info("No appointments for the next day.");
        } else {
            logger.info("Found {} users with appointments for the next day.", users.size());
            for (User user : users) {
                String emailBody = user.remindTheUser();
                String email = user.getEmail();
                String messageSubject = "Appointment Reminder";

                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

                    helper.setTo(email);
                    helper.setSubject(messageSubject);
                    helper.setText(emailBody, false);
                    helper.setFrom("", "doctor");
                    mailSender.send(mimeMessage);
                    logger.info("Email sent successfully to {}", user.getName());
                } catch (Exception e) {
                    logger.error("Failed to send email to {}: {}", user.getName(), e.getMessage(), e);
                }
            }
        }
    }
}