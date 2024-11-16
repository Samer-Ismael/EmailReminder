package com.sendingEmails.SendingEmails.config;

import com.sendingEmails.SendingEmails.entity.User;
import com.sendingEmails.SendingEmails.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
                StringBuilder emailBodyBuilder = new StringBuilder();

                // English version
                emailBodyBuilder.append("Dear ").append(user.getName()).append(",\n\n")
                        .append("This is a reminder for your appointment with the Doctor on ")
                        .append(user.getAppointment()).append(" at ").append(user.getTime()).append(".\n\n")
                        .append("Thank you!\n\n")
                        .append("-------------------------------------\n");

                // Swedish version
                emailBodyBuilder.append("Hej ").append(user.getName()).append(",\n\n")
                        .append("Det här är en påminnelse om ditt möte med doktorn den ")
                        .append(user.getAppointment()).append(" klockan ").append(user.getTime()).append(".\n\n")
                        .append("Tack!\n\n")
                        .append("-------------------------------------\n");

                // Somali version
                emailBodyBuilder.append("Mudane/Maro ").append(user.getName()).append(",\n\n")
                        .append("Tani waa xusuusin ku saabsan ballantaada dhakhtarka ee ")
                        .append(user.getAppointment()).append(" saacada ").append(user.getTime()).append(".\n\n")
                        .append("Mahadsanid!\n\n")
                        .append("-------------------------------------\n");

                // Arabic version
                emailBodyBuilder.append("عزيزي ").append(user.getName()).append("،\n\n")
                        .append("هذه تذكرة بموعدك مع الطبيب في ")
                        .append(user.getAppointment()).append(" الساعة ").append(user.getTime()).append(".\n\n")
                        .append("شكراً!\n");

                String emailBody = emailBodyBuilder.toString();

                String email = user.getEmail();
                String messageSubject = "Appointment Reminder";

                try {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(email);
                    message.setSubject(messageSubject);
                    message.setText(emailBody);
                    mailSender.send(message);
                    logger.info("Email sent successfully to {}", user.getName());
                } catch (Exception e) {
                    logger.error("Failed to send email to {}: {}", user.getName(), e.getMessage(), e);
                }

            }
        }
    }
}