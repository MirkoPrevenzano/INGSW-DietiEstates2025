
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
    public void sendAgentRegistrationEmail(String to) 
    {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("ciropizz2002@gmail.com");
        message.setTo(to);
        message.setSubject("BENVENUTO!");
        message.setText("CIAO...");

        javaMailSender.send(message);        
    }
    
}