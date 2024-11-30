package com.example.datatier.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datatier.model.PropertyRent;
import com.example.datatier.repository.PropertyRentRepository;

@Service
public class PropertyRentService {

    PropertyAgentService propertyAgentService;
    PropertyRentRepository propertyRentRepository;

    @Autowired
    public PropertyRentService(PropertyAgentService propertyAgentService,
                                    PropertyRentRepository propertyRentRepository){
        this.propertyAgentService=propertyAgentService;
        this.propertyRentRepository=propertyRentRepository;
    }

    public CompletableFuture<PropertyRent> save(PropertyRent propertyRent)
    {
        return CompletableFuture.completedFuture(propertyRentRepository.save(propertyRent));
    }

    public CompletableFuture<PropertyRent> saveNewPropertyRent(PropertyRent propertyRent,
                                            String usernamePropertyAgent)
    {
        return propertyAgentService.findByUsername(usernamePropertyAgent)
        .thenApply( agent->{
            if(agent.isPresent())
            {
                propertyRent.setPropertyAgent(agent.get());
                return propertyRent; //lo restituisce al prossimo theCompose
            }
            else
                throw new IllegalArgumentException("Agent don't found");
        
        })
        .thenCompose(property->save(propertyRent));
    }


}
