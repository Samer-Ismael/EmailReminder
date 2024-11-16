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

    //@Scheduled(cron = "0 4 23 * * ?") // for testing the app.
    @Scheduled(cron = "0 0 9 * * ?")  // Runs every day at 9:00 AM
    public void findUsersWithNextDayAppointments() {
        logger.info("Scheduled task triggered at {}", LocalDateTime.now());
        List<User> users = userService.findNextDayAppointments();

        if (users.isEmpty()) {
            logger.info("No appointments for the next day.");
        } else {
            logger.info("Found {} users with appointments for the next day.", users.size());
            for (User user : users) {
                String emailBody =
                        "Dear " + user.getName() + ",\n\n" +
                                "This is a reminder for your appointment with the Doctor on " + user.getAppointment() + ".\n\n" +
                                "Thank you!\n\n" +

                                "-------------------------------------\n" +

                                "Hej " + user.getName() + ",\n\n" +
                                "Det här är en påminnelse om ditt möte med doktorn den " + user.getAppointment() + ".\n\n" +
                                "Tack!\n\n" +

                                "-------------------------------------\n"+

                                "Mudane/Maro " + user.getName() + ",\n\n" +
                                "Tani waa xusuusin ku saabsan ballantaada dhakhtarka ee " + user.getAppointment() + ".\n\n" +
                                "Mahadsanid!\n\n" +

                                "-------------------------------------\n"+

                                "عزيزي " + user.getName() + "،\n\n" +
                                "هذه تذكرة بموعدك مع الطبيب في " + user.getAppointment() + ".\n\n" +
                                "شكراً!\n";

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