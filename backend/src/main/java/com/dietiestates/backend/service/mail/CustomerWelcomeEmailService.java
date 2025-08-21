
package com.dietiestates.backend.service.mail;

import org.springframework.stereotype.Service;

import com.dietiestates.backend.model.entity.User;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CustomerWelcomeEmailService extends UserWelcomeEmailService
{
    public CustomerWelcomeEmailService(EmailService emailService)
    {
        super(emailService);
    }

    
    @Override
    protected String getWelcomeBody(User user) 
    {
        return  String.format("""
                             "Ciao %s," 
                             "Grazie per esserti registrato nella nostra applicazione!"
                             "Inizia subito a cercare l'immobile perfetto per te, e non esitare a contattarci per qualsiasi dubbio o problema."
                             "Cordiali Saluti," 
                             "Staff DietiEstates2025."
                             """, user.getName());    
    }
    
}