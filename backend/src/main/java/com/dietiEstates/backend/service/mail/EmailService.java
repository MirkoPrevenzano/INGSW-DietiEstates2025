
package com.dietiEstates.backend.service.mail;


public interface EmailService 
{
    public void sendMail(String to, String subject, String body);
}