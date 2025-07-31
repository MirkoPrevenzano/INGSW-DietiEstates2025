
package com.dietiEstates.backend.service.mail;

public interface AgentWelcomeEmailService 
{
    void sendWelcomeEmail(String recipientEmail, String userName);
}