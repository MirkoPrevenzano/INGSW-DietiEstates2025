
package com.dietiEstates.backend.service.mail;

// Interfaccia per il servizio di email di Benvenuto

public interface AgentWelcomeEmailService 
{
    void sendWelcomeEmail(String recipientEmail, String userName);
}