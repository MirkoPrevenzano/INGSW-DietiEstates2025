package com.example.datatier.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.model.PropertyAgent;
import com.example.datatier.repository.PropertyAgentRepository;

import jakarta.transaction.Transactional;

@Service
public class PropertyAgentService {
    PropertyAgentRepository propertyAgentRepository;
    PasswordValidatorService passwordValidatorService;

    @Autowired
    public PropertyAgentService(PropertyAgentRepository propertyAgentRepository,
                                    PasswordValidatorService passwordValidatorService)
    {
        this.propertyAgentRepository=propertyAgentRepository;
        this.passwordValidatorService=passwordValidatorService;
    }

    @Async
    public CompletableFuture<Optional<PropertyAgent>> findByUsername(String username)
    {
        return CompletableFuture.completedFuture(propertyAgentRepository.findByUsername(username));
    }
    @Async
    public CompletableFuture<PropertyAgent> save(PropertyAgent propertyAgent)
    {
        return CompletableFuture.completedFuture(propertyAgentRepository.save(propertyAgent));
    }

   

    @Transactional
    @Async
    public CompletableFuture<PropertyAgent> saveNewAgent(PropertyAgent propertyAgent){
        return findByUsername(propertyAgent.getUsername())
        .thenCompose(newAgent -> {
            if (newAgent.isPresent())
            {
                throw new IllegalArgumentException("Username already in use");
            }
            if(passwordValidatorService.isValid(propertyAgent.getPassword())) 
                return save(propertyAgent);
            else 
                throw new IllegalArgumentException("Invalid password");
            })
        .exceptionally(ex -> {
            throw new IllegalArgumentException("Error: " + ex.getMessage());
        });
    }

}
