package com.vodafone.spring_swlzfw;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


//From: https://www.digitalocean.com/community/tutorials/javamail-example-send-mail-in-java-smtp
//Ver2 https://www.tutorialspoint.com/java/java_sending_email.htm
public class NotificationEmail {
    String to = "samuel.mvk@gmx.de";
    String from = "web@gmail.com";
    String host = "localhost";
    Properties properties = System.getProperties();
    // Setup mail server
//    properties.setProperty("mail.smtp.host",host);
    Session session = Session.getDefaultInstance(properties);

    public void sendEmail(Session session, String toEmail, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("This is the Subject Line!");
            message.setText("This is actual message");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
