
package com.dietiestates.backend.service.mail;

import org.springframework.scheduling.annotation.Async;


public interface EmailService 
{
    @Async
    public void sendEmail(String to, String subject, String body);
}