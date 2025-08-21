
package com.dietiestates.backend.service.mail;


public abstract class AgentAndCollaboratorWelcomeEmailService extends UserWelcomeEmailService 
{
    protected AgentAndCollaboratorWelcomeEmailService(EmailService emailService)
    {
        super(emailService);
    }

    
    protected abstract String getAgencyName(String username);
}