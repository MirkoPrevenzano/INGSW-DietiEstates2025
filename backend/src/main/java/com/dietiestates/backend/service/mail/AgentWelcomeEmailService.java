
package com.dietiestates.backend.service.mail;

import org.springframework.stereotype.Service;

import com.dietiestates.backend.model.entity.User;
import com.dietiestates.backend.repository.AgentRepository;

import lombok.extern.slf4j.Slf4j;


@Service
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
        return String.format("""
                             Ciao %s,
                             Sei stato registrato nella nostra applicazione in ruolo di AGENTE IMMOBILIARE dell'agenzia '%s'.
                             Inizia subito a caricare e gestire i tuoi annunci immobiliari, e non esitare a contattarci per qualsiasi dubbio o problema.
                             Cordiali Saluti,
                             Staff DietiEstates2025.
                             """, user.getName(), getAgencyName(user.getUsername()));
    }

    @Override
    protected String getAgencyName(String username) 
    {
        return agentRepository.findAgencyNameByUsername(username);
    }
}