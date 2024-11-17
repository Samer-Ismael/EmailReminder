package com.sendingEmails.SendingEmails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SendingEmailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendingEmailsApplication.class, args);
		openBrowser("http://localhost:8080");

	}

	private static void openBrowser(String url) {
		String os = System.getProperty("os.name").toLowerCase();

		try {
			if (os.contains("win")) {
				Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
			} else if (os.contains("mac")) {
				Runtime.getRuntime().exec(new String[]{"open", url});
			} else {
				System.err.println("Unsupported operating system: " + os);
			}
		} catch (Exception e) {
			System.err.println("Failed to open browser: " + e.getMessage());
		}
	}
}
