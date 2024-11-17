package com.sendingEmails.SendingEmails.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate appointment;
    private LocalTime time;


    public String welcomeTheUser (){

        StringBuilder message = new StringBuilder();

        // English version
        message.append("Dear ").append(getName()).append(",\n\n")
                .append("Thank you for booking an appointment with us! Here are the details of your appointment:\n\n")
                .append("Date: ").append(getAppointment()).append("\n")
                .append("Time: ").append(getTime()).append("\n\n")
                .append("If you have any questions, feel free to contact us.\n\n")
                .append("Best regards,\n")
                .append("The Appointment Team\n\n")
                .append("-------------------------------------\n");

        // Swedish version
        message.append("Hej ").append(getName()).append(",\n\n")
                .append("Tack för att du bokade ett möte hos oss! Här är informationen om ditt möte:\n\n")
                .append("Datum: ").append(getAppointment()).append("\n")
                .append("Tid: ").append(getTime()).append("\n\n")
                .append("Om du har några frågor, kontakta oss gärna.\n\n")
                .append("Vänliga hälsningar,\n")
                .append("Bokningsteamet\n\n")
                .append("-------------------------------------\n");

        // Somali version
        message.append("Mudane/Maro ").append(getName()).append(",\n\n")
                .append("Mahadsanid ballansashada dhakhtarka nala. Faahfaahinta ballantaada waa sidan:\n\n")
                .append("Taariikhda: ").append(getAppointment()).append("\n")
                .append("Waqtiga: ").append(getTime()).append("\n\n")
                .append("Haddii aad qabtid wax su'aalo ah, fadlan nala soo xiriir.\n\n")
                .append("Salaan diiran,\n")
                .append("Kooxda Ballansashada\n\n")
                .append("-------------------------------------\n");

        // Arabic version
        message.append("عزيزي ").append(getName()).append("،\n\n")
                .append("شكراً لحجز موعد مع طبيبنا. تفاصيل موعدك كالتالي:\n\n")
                .append("التاريخ: ").append(getAppointment()).append("\n")
                .append("الوقت: ").append(getTime()).append("\n\n")
                .append("إذا كان لديك أي أسئلة، فلا تتردد في الاتصال بنا.\n\n")
                .append("مع أطيب التحيات،\n")
                .append("فريق الحجز\n");

        return message.toString();

    }

    public String remindTheUser (){

        StringBuilder emailBodyBuilder = new StringBuilder();

        // English version
        emailBodyBuilder.append("Dear ").append(getName()).append(",\n\n")
                .append("This is a reminder for your appointment with the Doctor on ")
                .append(getAppointment()).append(" at ").append(getTime()).append(".\n\n")
                .append("Thank you!\n\n")
                .append("-------------------------------------\n");

        // Swedish version
        emailBodyBuilder.append("Hej ").append(getName()).append(",\n\n")
                .append("Det här är en påminnelse om ditt möte med doktorn den ")
                .append(getAppointment()).append(" klockan ").append(getTime()).append(".\n\n")
                .append("Tack!\n\n")
                .append("-------------------------------------\n");

        // Somali version
        emailBodyBuilder.append("Mudane/Maro ").append(getName()).append(",\n\n")
                .append("Tani waa xusuusin ku saabsan ballantaada dhakhtarka ee ")
                .append(getAppointment()).append(" saacada ").append(getTime()).append(".\n\n")
                .append("Mahadsanid!\n\n")
                .append("-------------------------------------\n");

        // Arabic version
        emailBodyBuilder.append("عزيزي ").append(getName()).append("،\n\n")
                .append("هذه تذكرة بموعدك مع الطبيب في ")
                .append(getAppointment()).append(" الساعة ").append(getTime()).append(".\n\n")
                .append("شكراً!\n");

        String emailBody = emailBodyBuilder.toString();
        return emailBody;
    }
}
