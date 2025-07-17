
package com.dietiEstates.backend.service.mail;

import com.dietiEstates.backend.model.entity.Customer;


public interface CustomerWelcomeEmailService 
{
    void sendWelcomeEmail(Customer customer);
}