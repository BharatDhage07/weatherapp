package com.weatherapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMessage(String to, String text) {        
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("pu.angelweekly@gmail.com");
        message.setTo(to); 
        message.setSubject("Weather Information"); 
        message.setText(text);
        emailSender.send(message);
    }
}
