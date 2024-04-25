package com.fertilizeo.service;

import com.fertilizeo.config.jwt.JwtTokenValidationUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtTokenValidationUtil jwtTokenValidationUtil;

    @Value("${mail.from}")
    private String fromMail;

    @Value("${frontend.url}")
    private String frontendUrl;




    // Confirmation e-mail d'inscription
    @Async
    public void sendEmail (String toEmail,
                           String name){
        String token = jwtTokenValidationUtil.generateToken(toEmail);
         //
        String link =" http://localhost:8080/compte/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(toEmail);
        message.setSubject("Confirmer votre addresse e-mail");
        message.setText("Bienvenue Chez Fertilizeo " + name + ",\n\nVeuillez clicker sur ce lien pour verifier votre adresse e-mail:\n" + link);
        javaMailSender.send(message);

    }

    //Email de confirmation pour réinitialisation des mots de passe

        public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException, MessagingException {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            javaMailSender.send(message);
        }
    }


