/* 
package com.dietiEstates.backend.service.mail;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class CollaboratorWelcomeEmailService implements UserWelcomeEmailService
{
    private final EmailService emailService;


    @Override
    public void sendWelcomeEmail(User user) 
    {
        String subject = "Benvenuto su DietiEstates2025!";
        String body = String.format("Ciao %s,\n\nSei stato registrato nella nostra applicazione in ruolo di COLLABORATORE DELL'AMMINISTRATORE!\n" +
                                    "Inizia subito a gestire l'agenzia controllando l'operato dei tuoi agenti immobiliari, e non esitare a contattarci per qualsiasi dubbio o problema.\n\n" +
                                    "Cordiali Saluti,\nStaff DietiEstates2025.", user.getName());

        emailService.sendEmail(user.getUsername(), subject, body);
    }
} */



package com.dietiEstates.backend.service.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.User;
import com.dietiEstates.backend.repository.AdministratorRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Qualifier("CollaboratorWelcomeEmailService")
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
        return String.format("Ciao %s,\n\n" + 
                             "Sei stato registrato nella nostra applicazione in ruolo di COLLABORATORE DELL'AMMINISTRATORE dell'agenzia '%s'.\n" +
                             "Inizia subito a gestire l'agenzia controllando l'operato dei tuoi agenti immobiliari, e non esitare a contattarci per qualsiasi dubbio o problema.\n\n" +
                             "Cordiali Saluti,\n" + 
                             "Staff DietiEstates2025.", user.getName(), getAgencyName(user.getUsername()));
    }

    @Override
    protected String getAgencyName(String username) 
    {
        return administratorRepository.findAgencyNameByUsername(username);
    }
}