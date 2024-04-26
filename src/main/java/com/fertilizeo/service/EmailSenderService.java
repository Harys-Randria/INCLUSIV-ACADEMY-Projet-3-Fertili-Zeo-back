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
    public void sendEmail(String toEmail, String name) throws MessagingException {
        String token = jwtTokenValidationUtil.generateToken(toEmail);
        String link = "http://localhost:8080/compte/verify?token=" + token;
        String termsLink = "https://docs.google.com/document/d/1WaqgRbOCfFSTOBH2Z0IchA3K81F2TpIcng-nn6Y-c6A/edit?usp=sharing";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromMail);
        helper.setTo(toEmail);
        helper.setSubject("Bienvenue chez Fertilizeo - Veuillez confirmer votre inscription");

        String htmlMsg = "<h1>Bienvenue chez Fertilizeo, " + name + "!</h1>"
                + "<p>Merci de vous joindre à nous. Veuillez confirmer votre adresse e-mail en cliquant sur le bouton ci-dessous:</p>"
                + "<a href='" + link + "' style='padding: 10px; background-color: blue ; color: white; text-decoration: none; border-radius: 5px;'>Confirmer Email</a>"
                + "<p>En confirmant votre adresse e-mail, vous consentez à l'utilisation de vos données personnelles conformément à notre <a href='" + termsLink + "' style='color: blue;'>Politique de Confidentialité</a>.</p>"
                + "<p>Il est important de lire et comprendre notre politique de confidentialité pour être informé sur la manière dont nous utilisons vos données personnelles et les droits que vous avez à cet égard.</p>"
                + "<p>Nous vous remercions pour votre confiance et nous nous engageons à protéger vos données personnelles.</p>"

                + "<p>Cordialement, <br/> L'équipe Fertilizeo</p>";



        helper.setText(htmlMsg, true);

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






