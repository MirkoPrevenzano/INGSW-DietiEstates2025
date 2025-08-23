
package com.dietiestates.backend.service.mail;

import org.springframework.stereotype.Service;

import com.dietiestates.backend.model.entity.User;
import com.dietiestates.backend.repository.AdministratorRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CollaboratorWelcomeEmailService extends AgentAndCollaboratorWelcomeEmailService
{
    private final AdministratorRepository administratorRepository;

    
    public CollaboratorWelcomeEmailService(EmailService emailService, AdministratorRepository administratorRepository)
    {
        super(emailService);
        this.administratorRepository = administratorRepository;
    }

    
    @Override
    protected String getWelcomeBody(User user) 
    {
        return String.format("""
                             Ciao %s, 
                             Sei stato registrato nella nostra applicazione in ruolo di COLLABORATORE DELL'AMMINISTRATORE dell'agenzia '%s'.
                             Inizia subito a gestire l'agenzia controllando l'operato dei tuoi agenti immobiliari, e non esitare a contattarci per qualsiasi dubbio o problema.
                             Cordiali Saluti,
                             Staff DietiEstates2025.
                             """, user.getName(), getAgencyName(user.getUsername()));
    }

    @Override
    protected String getAgencyName(String username) 
    {
        return administratorRepository.findAgencyNameByUsername(username);
    }
}