# SendingEmails Application

This application sends email reminders for user appointments that are scheduled for the next day. The emails are sent using SMTP (Gmail in this case), and the user must configure the database and email settings before running the application.

This application is designed to manage user bookings and send automated email notifications for appointment-related events. Key features include:
- Sending welcome emails to users upon registering a new booking.
- Automatically checking for and deleting appointments that are older than one week.
- Sending reminder emails for appointments scheduled for the next day.
- A simple frontend interface for managing users, bookings, and appointments.

The application uses H2 database for storage and Gmail SMTP for sending emails.


## Prerequisites

Before running the application, you need to set up the following:

1. **Email Configuration (Gmail)**  
   This application uses Gmail SMTP to send emails. You need to create an **App Password** for your Google account to use it in the application.

## Setup Instructions

### 1. Update `application.properties`

Open the `application.properties` file located in the `src/main/resources` directory and update the following properties:


#### Set the SMTP settings for Gmail
```properties
spring.mail.username=your_gmail_address@gmail.com
spring.mail.password=your_app_password
```

## 2. Create an App Password for Gmail

Gmail requires an App Password to allow third-party applications to send emails using your Gmail account. Follow these steps to create one:

1. Sign in to your Google account at [Google Account](https://myaccount.google.com/).
2. Go to the **Security** tab on the left menu, or search for **App password**
3. Under the **"Signing in to Google"** section, make sure **2-Step Verification** is enabled. If not, enable it and follow the prompts.
4. Once 2-Step Verification is enabled, you will see an option for **App Passwords**.
5. Click on **App Passwords**.
6. You may need to sign in again. Once signed in, you will be able to create a new app password.
7. Select **Mail** as the app and **Other (Custom name)** for the device.
8. Enter a custom name (e.g., `SendingEmailsApp`) and click **Generate**.
9. Copy the **App Password** that is generated.

## Accessing the H2 Database Console

This application uses an H2 database, which can be accessed through a web-based console. Follow the steps below to access the H2 console:

1. **Start the Application**: Open your web browser and navigate to the following URL:

   ```bash
   http://localhost:8090/h2-console
   ```

- **JDBC URL: jdbc:h2:file:~/DocktorDB**
- **Username: root**
- **Password: Database123**