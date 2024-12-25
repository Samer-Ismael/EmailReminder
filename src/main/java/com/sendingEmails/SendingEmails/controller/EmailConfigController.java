package com.sendingEmails.SendingEmails.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class EmailConfigController {

    @PostMapping("/update")
    public ResponseEntity<String> updateEmailConfig(
            @RequestParam String username,
            @RequestParam String password) {

        // Save the credentials securely, e.g., updating environment variables or a secure vault
        System.setProperty("spring.mail.username", username);
        System.setProperty("spring.mail.password", password);

        return ResponseEntity.ok("Email configuration updated successfully.");
    }
}
