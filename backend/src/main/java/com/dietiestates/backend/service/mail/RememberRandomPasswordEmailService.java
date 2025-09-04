
package com.dietiestates.backend.service.mail;

import org.springframework.stereotype.Service;

import com.dietiestates.backend.model.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class RememberRandomPasswordEmailService 
{
    private final EmailService emailService;



    public void sendRandomPasswordEmail(User user, String randomPassword) 
    {
        String subject = "La tua password di default in DietiEstates2025!";
        String body = String.format("""
                                    Ciao %s,
                                    Ti ricordiamo che il tuo account è stato creato con una password di default, per ragioni di sicurezza, che è la seguente: %s.
                                    Effettua il tuo primo accesso nell'applicazione per cambiarla a tuo gradimento, ricordandoti di inserire la password che ti abbiamo inviato in questa mail! 
                                    Cordiali Saluti 
                                    Staff DietiEstates2025.
                                    """, user.getName(), randomPassword);

        emailService.sendEmail(user.getUsername(), subject, body);
    }
}
