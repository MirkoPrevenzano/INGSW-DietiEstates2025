
package com.dietiestates.backend.service.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dietiestates.backend.exception.EmailServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceDefaultSpringImpl implements EmailService
{
    private final JavaMailSender javaMailSender;

    private static final String DIETI_ESTATES_EMAIL = "ciropizza2002@gmail.com";



    @Override
    public void sendEmail(String recipient, String subject, String body) 
    {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        message.setFrom(DIETI_ESTATES_EMAIL);

        try 
        {
            javaMailSender.send(message);

            log.info("Email inviata correttamente a: " + recipient + "\nOggetto: " + subject);
        } 
        catch (MailException e) 
        {
            throw new EmailServiceException(recipient, subject, e);
        }
    }   
}