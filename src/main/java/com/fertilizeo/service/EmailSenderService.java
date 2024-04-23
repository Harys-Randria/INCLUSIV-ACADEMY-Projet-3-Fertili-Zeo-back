package com.fertilizeo.service;

import com.fertilizeo.config.jwt.JwtTokenValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtTokenValidationUtil jwtTokenValidationUtil;

    @Value("${mail.from}")
    private String fromMail;

    @Async
    public void sendEmail (String toEmail,
                           String name){
        String token = jwtTokenValidationUtil.generateToken(toEmail);
        String link = "http://localhost:8080/compte/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(toEmail);
        message.setSubject("Confirmer votre addresse e-mail");
        message.setText("Bienvenue Chez Fertilizeo " + name + ",\n\nVeuillez clicker sur ce lien pour verifier votre adresse e-mail:\n" + link);
        javaMailSender.send(message);
    }
}
