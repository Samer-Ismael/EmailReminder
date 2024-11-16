package com.sendingEmails.SendingEmails.service;

import com.sendingEmails.SendingEmails.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

        String emailBody = generateWelcomeEmailBody(user);
        String subject = "Welcome to Our Appointment System";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(emailBody);

        mailSender.send(message);
    }

    private String generateWelcomeEmailBody(User user) {
        return "Dear " + user.getName() + ",\n\n" +
                "Thank you for booking an appointment with us! Here are the details of your appointment:\n\n" +
                "Date: " + user.getAppointment() + "\n" +
                "Time: " + user.getTime() + "\n\n" +
                "If you have any questions, feel free to contact us.\n\n" +
                "Best regards,\n" +
                "The Appointment Team\n\n" +
                "-------------------------------------\n" +
                "Hej " + user.getName() + ",\n\n" +
                "Tack för att du bokade ett möte hos oss! Här är informationen om ditt möte:\n\n" +
                "Datum: " + user.getAppointment() + "\n" +
                "Tid: " + user.getTime() + "\n\n" +
                "Om du har några frågor, kontakta oss gärna.\n\n" +
                "Vänliga hälsningar,\n" +
                "Bokningsteamet\n\n" +
                "-------------------------------------\n" +
                "Mudane/Maro " + user.getName() + ",\n\n" +
                "Mahadsanid ballansashada dhakhtarka nala. Faahfaahinta ballantaada waa sidan:\n\n" +
                "Taariikhda: " + user.getAppointment() + "\n" +
                "Waqtiga: " + user.getTime() + "\n\n" +
                "Haddii aad qabtid wax su'aalo ah, fadlan nala soo xiriir.\n\n" +
                "Salaan diiran,\n" +
                "Kooxda Ballansashada\n\n" +
                "-------------------------------------\n" +
                "عزيزي " + user.getName() + "،\n\n" +
                "شكراً لحجز موعد مع طبيبنا. تفاصيل موعدك كالتالي:\n\n" +
                "التاريخ: " + user.getAppointment() + "\n" +
                "الوقت: " + user.getTime() + "\n\n" +
                "إذا كان لديك أي أسئلة، فلا تتردد في الاتصال بنا.\n\n" +
                "مع أطيب التحيات،\n" +
                "فريق الحجز\n";
    }
}

