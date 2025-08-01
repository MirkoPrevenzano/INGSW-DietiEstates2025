
package com.dietiEstates.backend.service.mail;

import com.dietiEstates.backend.model.entity.User;


public interface UserWelcomeEmailService 
{
    void sendWelcomeEmail(User user);
}