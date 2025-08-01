
package com.dietiEstates.backend.service.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.exception.EmailServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceDefaultSpringImpl implements EmailService
{
    private final JavaMailSender javaMailSender;


    @Override
    public void sendEmail(String to, String subject, String body) 
    {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        message.setFrom("ciropizza2002@gmail.com");

        try 
        {
            javaMailSender.send(message);
        } 
        catch (MailException e) 
        {
            throw new EmailServiceException("Failed to send email", e);
        }

        log.info("Mail inviata correttamente a: " + to + "\nOggetto: " + subject);
    }   
}