package com.example.datatier.service.property_agent_service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datatier.model.Administrator;
import com.example.datatier.model.PropertyAgent;
import com.example.datatier.service.PasswordValidatorService;
import com.example.datatier.service.administrator_service.AdministratorAsyncService;

import jakarta.transaction.Transactional;

@Service
public class PropertyAgentService {
    PasswordValidatorService passwordValidatorService;
    AdministratorAsyncService administratorAsyncService;
    PropertyAgentAsyncService propertyAgentAsyncService;

    @Autowired
    public PropertyAgentService(PasswordValidatorService passwordValidatorService,
                                AdministratorAsyncService administratorAsyncService,
                                PropertyAgentAsyncService propertyAgentAsyncService)
    {
        this.passwordValidatorService=passwordValidatorService;
        this.administratorAsyncService=administratorAsyncService;
        this.propertyAgentAsyncService=propertyAgentAsyncService;
    }

    

   

    @Transactional
    public CompletableFuture<PropertyAgent> saveNewAgent(String username,String password, String usernameAdmin){
        return propertyAgentAsyncService.findByUsername(username)
        .thenCompose(newAgent -> {
            if (newAgent.isPresent())
            {
                throw new IllegalArgumentException("Username already in use");
            }
            return administratorAsyncService.findByUsername(usernameAdmin);
            })
        .thenCompose(admin -> {
            if (admin.isEmpty()) {
                throw new IllegalArgumentException("Administrator not found");
            }
            if (passwordValidatorService.isValid(password)) {
                PropertyAgent propertyAgent = generatePropertyAgent(admin.get(), username, password);
                return propertyAgentAsyncService.save(propertyAgent);
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        })
        .exceptionally(ex -> {
            throw new IllegalArgumentException("Error: " + ex.getMessage());
        });
    }

    private PropertyAgent generatePropertyAgent(Administrator admin, String username, String password) {
        PropertyAgent propertyAgent=new PropertyAgent();
        propertyAgent.setAdministrator(admin);
        propertyAgent.setPassword(password);
        propertyAgent.setUsername(username);
        return propertyAgent;
    }

}
