package com.skylena.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

	public void sendOtp(String recipientEmail, String otp) {
		// Set up mail server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587"); // TLS port

		// Create a session with an authenticator
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				// Replace with your Gmail credentials
                return new PasswordAuthentication("sagaruk143@gmail.com", "vqgg etei dqze nmpi"); 
			}
		});

		try {
			// Create a MimeMessage
			Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sagaruk143@gmail.com"));  // Sender's email
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail) // Valid recipient's
																									// email from the
																									// database
			);
			message.setSubject("Here's your login OTP");
			
            message.setText("Hi, Use the One Time Password (OTP) given below to log in to your Skylena account Valid for 2 min : " + otp);

			// Send the email
			Transport.send(message);

			System.out.println("OTP sent successfully to " + recipientEmail);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
