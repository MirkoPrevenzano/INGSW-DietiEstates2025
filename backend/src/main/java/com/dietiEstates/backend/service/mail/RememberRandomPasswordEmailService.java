
package com.dietiEstates.backend.service.mail;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class RememberRandomPasswordEmailService 
{
    private final EmailService emailService;


    public void sendRandomPasswordEmail(User user) 
    {
        String subject = "La tua password di default in DietiEstates2025!";
        String body = String.format("Ciao %s,\n\n" + 
                                    "Ti ricordiamo che il tuo account è stato creato con una password di default, per ragioni di sicurezza, che è la seguente: %s.\n" +
                                    "Effettua il tuo primo accesso nell'applicazione per cambiarla a tuo gradimento, ricordandoti di inserire la password che ti abbiamo inviato in questa mail!\n\n" +
                                    "Cordiali Saluti\n" + 
                                    "Staff DietiEstates2025.", user.getName(), user.getPassword());

        emailService.sendEmail(user.getUsername(), subject, body);
    }
}
