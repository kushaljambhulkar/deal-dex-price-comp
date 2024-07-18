package com.springboot.price.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void setLoginNotification(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Deal-Dex Login Notification");
        message.setText("Dear " + username + ",\n\nYou have successfully logged in on Deal-Dex.\n\nBest Regards,\nYour Deal-Dex Team");
        javaMailSender.send(message);
    }
}
