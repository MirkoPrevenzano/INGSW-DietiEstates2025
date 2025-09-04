
package com.dietiestates.backend.service.mail;

import com.dietiestates.backend.model.entity.User;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class UserWelcomeEmailService 
{
    private final EmailService emailService;



    public final void sendWelcomeEmail(User user)
    {
        String subject = "Benvenuto su DietiEstates2025!";
        String body = getWelcomeBody(user);

        emailService.sendEmail(user.getUsername(), subject, body);
    }


    
    protected abstract String getWelcomeBody(User user);
}