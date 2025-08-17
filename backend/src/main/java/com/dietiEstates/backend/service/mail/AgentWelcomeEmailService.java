/* 
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
} */



package com.dietiEstates.backend.service.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.User;
import com.dietiEstates.backend.repository.AgentRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Qualifier("AgentWelcomeEmailService")
@Slf4j
public class AgentWelcomeEmailService extends AgentAndCollaboratorWelcomeEmailService
{
    private final AgentRepository agentRepository;

    
    public AgentWelcomeEmailService(EmailService emailService, AgentRepository agentRepository)
    {
        super(emailService);
        this.agentRepository = agentRepository;
    }
    

    @Override
    protected String getWelcomeBody(User user) 
    {
        return String.format("Ciao %s,\n\n" + 
                             "Sei stato registrato nella nostra applicazione in ruolo di AGENTE IMMOBILIARE dell'agenzia '%s'.\n" +
                             "Inizia subito a caricare e gestire i tuoi annunci immobiliari, e non esitare a contattarci per qualsiasi dubbio o problema.\n\n" +
                             "Cordiali Saluti,\n" + 
                             "Staff DietiEstates2025.", user.getName(), getAgencyName(user.getUsername()));
    }

    @Override
    protected String getAgencyName(String username) 
    {
        String agencyname = agentRepository.findAgencyNameByUsername(username);
        log.info("\n\n\n\n {} \b\b\b", agencyname); 
        return agentRepository.findAgencyNameByUsername(username);
    }
}