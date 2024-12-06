package com.example.datatier.service.property_service.property_rent_service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.dto.AddressDTO;
import com.example.datatier.dto.PropertyRentDTO;
import com.example.datatier.model.PropertyRent;
import com.example.datatier.service.AddressService;
import com.example.datatier.service.property_agent_service.PropertyAgentAsyncService;
import com.example.datatier.service.property_service.PropertyService;

import jakarta.transaction.Transactional;

@Service
public class PropertyRentService implements PropertyService<PropertyRentDTO>{

    private final PropertyAgentAsyncService propertyAgentAsyncService;
    private final PropertyRentAsyncService propertyRentAsyncService;
    private final ModelMapper modelMapper;
    private final AddressService addressService;

    @Autowired
    public PropertyRentService(PropertyAgentAsyncService propertyAgentAsyncService,
                               PropertyRentAsyncService propertyRentAsyncService,
                               ModelMapper modelMapper,
                               AddressService addressService) {
        this.propertyAgentAsyncService = propertyAgentAsyncService;
        this.propertyRentAsyncService = propertyRentAsyncService;
        this.modelMapper = modelMapper;
        this.addressService=addressService;
    }

    @Override
    @Transactional
    public CompletableFuture<PropertyRentDTO> saveNewPropertyRent(PropertyRentDTO propertyRentDTO,
                                                                  String usernamePropertyAgent,AddressDTO addressDTO) {
        return propertyAgentAsyncService.findByUsername(usernamePropertyAgent)
        .thenCompose(agent -> {
            if (agent.isPresent()) {
                   
                return addressService.save(addressDTO)
                .thenCompose(address->{
                
                    PropertyRent propertyRent = modelMapper.map(propertyRentDTO, PropertyRent.class);
                    propertyRent.setPropertyAgent(agent.get());
                    propertyRent.setAddress(address);
                    return propertyRentAsyncService.save(propertyRent);
                });
                    
            } else {
                throw new IllegalArgumentException("Agent not found");
            }
        })
        .thenApply(propertyRentSave->modelMapper.map(propertyRentSave,PropertyRentDTO.class))
        .exceptionally(ex->{
            throw new IllegalArgumentException("Error:"+ex.getMessage());
        });
    }

    @Async 
    public CompletableFuture<Optional<List<PropertyRentDTO>>> getPropertyRents(Long page, Long number) {
        
        return CompletableFuture.completedFuture(Optional.empty());
    }
}