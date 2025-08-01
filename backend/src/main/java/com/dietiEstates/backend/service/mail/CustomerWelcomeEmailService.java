
package com.dietiEstates.backend.service.mail;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.User;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomerWelcomeEmailService implements UserWelcomeEmailService 
{
    private final EmailService emailService;


    @Override
    public void sendWelcomeEmail(User user) 
    {
        String subject = "Benvenuto su DietiEstates2025!";
        String body = String.format("Ciao %s,\n\nGrazie per esserti registrato nella nostra applicazione!\n" +
                                    "Inizia subito a cercare l'immobile perfetto per te, e non esitare a contattarci per qualsiasi dubbio o problema.\n\n" +
                                    "Cordiali Saluti,\nStaff DietiEstates2025.", user.getName());

        emailService.sendEmail(user.getUsername(), subject, body);
    }
}