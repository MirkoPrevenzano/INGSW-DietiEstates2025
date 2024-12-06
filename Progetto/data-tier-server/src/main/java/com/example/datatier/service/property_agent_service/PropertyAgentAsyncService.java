package com.example.datatier.service.property_agent_service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.model.PropertyAgent;
import com.example.datatier.model.repository.PropertyAgentRepository;

@Service
public class PropertyAgentAsyncService {
    PropertyAgentRepository propertyAgentRepository;

    @Autowired
    public PropertyAgentAsyncService(PropertyAgentRepository propertyAgentRepository){
        this.propertyAgentRepository=propertyAgentRepository;
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

}
