package com.example.datatier.service.property_service.property_sell_service;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.dto.AddressDTO;
import com.example.datatier.dto.PropertySellDTO;
import com.example.datatier.model.PropertySell;
import com.example.datatier.service.AddressService;
import com.example.datatier.service.property_agent_service.PropertyAgentAsyncService;
import com.example.datatier.service.property_service.PropertyService;

@Service
public class PropertySellService implements PropertyService<PropertySellDTO> {
    

    private final PropertyAgentAsyncService propertyAgentAsyncService;
    private final PropertySellAsyncService propertySellAsyncService;
    private final ModelMapper modelMapper;
    private final AddressService addressService;

    @Autowired
    public PropertySellService(PropertyAgentAsyncService propertyAgentAsyncService,
                                    PropertySellAsyncService  propertySellAsyncService,
                                    ModelMapper modelMapper,
                                    AddressService addressService){
        this.propertyAgentAsyncService=propertyAgentAsyncService;
        this.propertySellAsyncService=propertySellAsyncService;
        this.modelMapper=modelMapper;
        this.addressService=addressService;
    }

    @Override
    @Async
    public CompletableFuture<PropertySellDTO> saveNewProperty(PropertySellDTO propertySellDTO,
                                                                  String usernamePropertyAgent,
                                                                  AddressDTO addressDTO) {
        return propertyAgentAsyncService.findByUsername(usernamePropertyAgent)
        .thenApply(agent -> {
            if (agent.isPresent()) {
                return addressService.save(addressDTO)
                .thenCompose(address->{
                    PropertySell propertySell = modelMapper.map(propertySellDTO, PropertySell.class);
                    propertySell.setPropertyAgent(agent.get());
                    propertySell.setAddress(address);
                    return propertySellAsyncService.save(propertySell); // lo restituisce al prossimo thenCompose
                });
                
                
            } else {
                throw new IllegalArgumentException("Agent not found");
            }
        })
        .thenApply(savedPropertySell -> modelMapper.map(savedPropertySell, PropertySellDTO.class))
        .exceptionally(ex->{
            throw new IllegalArgumentException("Error"+ex.getMessage());
        });
    }


    
}
