package com.sendingEmails.SendingEmails.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate appointment;
    private LocalTime time;


    public String welcomeTheUser() {

        // English version

        String message = "Dear " + getName() + ",\n\n" + "Thank you for booking an appointment with us! Here are the details of your appointment:\n\n" + "Date: " + getAppointment() + "\n" + "Time: " + getTime() + "\n\n" + "If you have any questions, feel free to contact us.\n\n" + "Best regards,\n" + "The Appointment Team\n\n" + "-------------------------------------\n" +

                // Swedish version
                "Hej " + getName() + ",\n\n" + "Tack för att du bokade ett möte hos oss! Här är informationen om ditt möte:\n\n" + "Datum: " + getAppointment() + "\n" + "Tid: " + getTime() + "\n\n" + "Om du har några frågor, kontakta oss gärna.\n\n" + "Vänliga hälsningar,\n" + "Bokningsteamet\n\n" + "-------------------------------------\n" +

                // Somali version
                "Mudane/Maro " + getName() + ",\n\n" + "Mahadsanid ballansashada dhakhtarka nala. Faahfaahinta ballantaada waa sidan:\n\n" + "Taariikhda: " + getAppointment() + "\n" + "Waqtiga: " + getTime() + "\n\n" + "Haddii aad qabtid wax su'aalo ah, fadlan nala soo xiriir.\n\n" + "Salaan diiran,\n" + "Kooxda Ballansashada\n\n" + "-------------------------------------\n" +

                // Arabic version
                "عزيزي " + getName() + "،\n\n" + "شكراً لحجز موعد مع طبيبنا. تفاصيل موعدك كالتالي:\n\n" + "التاريخ: " + getAppointment() + "\n" + "الوقت: " + getTime() + "\n\n" + "إذا كان لديك أي أسئلة، فلا تتردد في الاتصال بنا.\n\n" + "مع أطيب التحيات،\n" + "فريق الحجز\n";

        return message;

    }

    public String remindTheUser() {

        // English version

        String emailBody = "Dear " + getName() + ",\n\n" + "This is a reminder for your appointment with the Doctor on " + getAppointment() + " at " + getTime() + ".\n\n" + "Thank you!\n\n" + "-------------------------------------\n" +

                // Swedish version
                "Hej " + getName() + ",\n\n" + "Det här är en påminnelse om ditt möte med doktorn den " + getAppointment() + " klockan " + getTime() + ".\n\n" + "Tack!\n\n" + "-------------------------------------\n" +

                // Somali version
                "Mudane/Maro " + getName() + ",\n\n" + "Tani waa xusuusin ku saabsan ballantaada dhakhtarka ee " + getAppointment() + " saacada " + getTime() + ".\n\n" + "Mahadsanid!\n\n" + "-------------------------------------\n" +

                // Arabic version
                "عزيزي " + getName() + "،\n\n" + "هذه تذكرة بموعدك مع الطبيب في " + getAppointment() + " الساعة " + getTime() + ".\n\n" + "شكراً!\n";
        return emailBody;
    }
}
