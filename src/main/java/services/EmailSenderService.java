package services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSenderService {

    private final String host;
    private final String port;
    private final String username;
    private final String password;

    public EmailSenderService(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void sendPasswordResetEmail(String recipientEmail, String resetCode) throws MessagingException {
        // Email message details
        String subject = "Password Reset Request";
        String body = "Dear User,\n\nTo reset your password, please use the following code:\n\n" + resetCode + "\n\nThis code will expire in 1 hour.\n\nBest regards,\nYour App Team";
        System.out.println("Email body: " + body); // Print the email body with the reset code

        // Send the email
        sendEmail(recipientEmail, subject, body);
    }



    private void sendEmail(String recipient, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
        System.out.println("Email sent successfully.");
    }
}
