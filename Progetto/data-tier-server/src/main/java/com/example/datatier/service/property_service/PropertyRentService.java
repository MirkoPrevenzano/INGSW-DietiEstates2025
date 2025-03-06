package com.example.datatier.service.property_service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datatier.dto.AddressDTO;
import com.example.datatier.dto.PropertyRentDTO;
import com.example.datatier.model.Address;
import com.example.datatier.model.PropertyAgent;
import com.example.datatier.model.PropertyRent;
import com.example.datatier.model.repository.PropertyRentRepository;
import com.example.datatier.service.AddressService;
import com.example.datatier.service.PropertyAgentService;

import jakarta.transaction.Transactional;

@Service
public class PropertyRentService implements PropertyService<PropertyRent,PropertyRentDTO>{

    private final PropertyAgentService propertyAgentService;
    private final PropertyRentRepository propertyRentRepository;
    private final ModelMapper modelMapper;
    private final AddressService addressService;

    @Autowired
    public PropertyRentService(PropertyAgentService propertyAgentService,
                               PropertyRentRepository propertyRentRepository,
                               ModelMapper modelMapper,
                                AddressService addressService) {
        this.propertyAgentService = propertyAgentService;
        this.propertyRentRepository = propertyRentRepository;
        this.modelMapper = modelMapper;
        this.addressService=addressService;
    }

    @Override
    @Transactional
    public PropertyRentDTO saveNewProperty(PropertyRentDTO propertyRentDTO,
                                                                  String usernamePropertyAgent,AddressDTO addressDTO) {
        Optional<PropertyAgent> agent= propertyAgentService.findByUsername(usernamePropertyAgent);
        
        if (agent.isPresent()) {
                
                Address address= addressService.save(addressDTO);
            
                PropertyRent propertyRent = modelMapper.map(propertyRentDTO, PropertyRent.class);
                propertyRent.setPropertyAgent(agent.get());
                propertyRent.setAddress(address);
                PropertyRent propertyRentSave= save(propertyRent);
                return modelMapper.map(propertyRentSave,PropertyRentDTO.class);
                
        } else {
            throw new IllegalArgumentException("Agent not found");
        }
        
    }

    public Optional<List<PropertyRentDTO>> getPropertyRents(Long page, Long number) {
        
        return Optional.empty();
    }

    @Override
    public PropertyRent save(PropertyRent propertyRent)
    {
        return propertyRentRepository.save(propertyRent);
    }

    @Override
    public Optional<PropertyRent> findById(Long id)
    {
        return propertyRentRepository.findById(id);
    }
}