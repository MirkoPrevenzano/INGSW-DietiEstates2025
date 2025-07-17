
package com.dietiEstates.backend.service.mail;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.Customer;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomerWelcomeEmailServiceImpl implements CustomerWelcomeEmailService 
{
    private final EmailService emailService;


    @Override
    public void sendWelcomeEmail(Customer customer) 
    {
        String subject = "Benvenuto su DietiEstates2025!";
        String body = String.format("Ciao %s,\n\nGrazie per esserti registrato nella nostra applicazione!\n" +
                                    "Inizia subito col cercare l'immobile perfetto per te, e non esitare a contattarci per qualsiasi dubbio o problema.\n\n" +
                                    "Saluti,", customer.getName());

        emailService.sendMail(customer.getUsername(), subject, body);
    }
}