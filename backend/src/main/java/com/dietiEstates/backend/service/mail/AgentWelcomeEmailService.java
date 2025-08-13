
package com.dietiEstates.backend.service.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.User;

import lombok.extern.slf4j.Slf4j;


@Service
@Qualifier("AgentWelcomeEmailService")
@Slf4j
public class AgentWelcomeEmailService extends UserWelcomeEmailService
{
    public AgentWelcomeEmailService(EmailService emailService)
    {
        super(emailService);
    }

    
    
    @Override
    protected String getWelcomeBody(User user) 
    {
        return  String.format("Ciao %s,\n\nSei stato registrato nella nostra applicazione in ruolo di AGENTE IMMOBILIARE!\n" +
                                    "Inizia subito a caricare e gestire i tuoi annunci immobiliari, e non esitare a contattarci per qualsiasi dubbio o problema.\n\n" +
                                    "Cordiali Saluti,\nStaff DietiEstates2025.", user.getName());
    }
}