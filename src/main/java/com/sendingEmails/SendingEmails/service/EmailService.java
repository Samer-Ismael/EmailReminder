package com.sendingEmails.SendingEmails.service;

import com.sendingEmails.SendingEmails.entity.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(User user) {
        if (user == null || user.getEmail() == null) {
            throw new IllegalArgumentException("User or email cannot be null");
        }

        String emailBody = user.welcomeTheUser();
        String subject = "Welcome to Our Appointment System";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(emailBody, false);
            helper.setFrom("noreply@mail.docktor.se", "Doctor");
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

