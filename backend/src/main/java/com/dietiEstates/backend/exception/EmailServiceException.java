
package com.dietiEstates.backend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class EmailServiceException extends RuntimeException
{
    private final String recipient;
    private final String subject;


    public EmailServiceException(String msg)
    {
        this(msg, new String(""), new String(""), null);
    }
    
    public EmailServiceException(String msg, Throwable cause)
    {
        this(msg, new String(""), new String(""), cause);
    }

    public EmailServiceException(String recipient, String subject)
    {
        this(null, recipient, subject, null);
    }

    public EmailServiceException(String recipient, String subject, Throwable cause)
    {
        this(null, recipient, subject, cause);
    }

    public EmailServiceException(String msg, String recipient, String subject)
    {
        this(msg, recipient, subject, null);
    }

    public EmailServiceException(String msg, String recipient, String subject, Throwable cause)
    {
        super(msg, cause);
        this.recipient = recipient;
        this.subject = subject;
    }


    @Override
    public String getMessage() 
    {
        String baseMessage = super.getMessage();
        StringBuilder fullMessage = new StringBuilder();
        
        if (baseMessage != null && !baseMessage.isEmpty()) 
            fullMessage.append(baseMessage);
        else 
            fullMessage.append("Errore durante l'invio dell'email!");

        
        if (recipient != null) 
            fullMessage.append("\nDestinatario: ").append(recipient);
        
        if (subject != null) 
            fullMessage.append("\nOggetto: ").append(subject);
        
        
        return fullMessage.toString();
    }
}