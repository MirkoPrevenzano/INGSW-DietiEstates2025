
package com.dietiEstates.backend.service.mail;


public interface EmailService 
{
    public void sendEmail(String to, String subject, String body);
}