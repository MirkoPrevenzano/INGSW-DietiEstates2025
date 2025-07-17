
package com.dietiEstates.backend.service.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultSpringEmailService implements EmailService
{
    private final JavaMailSender javaMailSender;



    @Override
    public void sendMail(String to, String subject, String body) 
    {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        message.setFrom("ciropizza2002@gmail.com");

        javaMailSender.send(message);

        System.out.println("Mail inviata correttamente a: " + to + " Oggetto: " + subject);
    }   
}