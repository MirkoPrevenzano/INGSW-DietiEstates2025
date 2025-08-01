
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
}