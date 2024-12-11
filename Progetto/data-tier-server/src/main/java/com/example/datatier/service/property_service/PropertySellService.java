package com.example.datatier.service.property_service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datatier.dto.AddressDTO;
import com.example.datatier.dto.PropertySellDTO;
import com.example.datatier.model.Address;
import com.example.datatier.model.PropertyAgent;
import com.example.datatier.model.PropertySell;
import com.example.datatier.model.repository.PropertySellRepository;
import com.example.datatier.service.AddressService;
import com.example.datatier.service.PropertyAgentService;

@Service
public class PropertySellService implements PropertyService<PropertySell, PropertySellDTO> {
    

    private final PropertyAgentService propertyAgentService;
    private final PropertySellRepository propertySellRepository;
    private final ModelMapper modelMapper;
    private final AddressService addressService;

    @Autowired
    public PropertySellService(PropertyAgentService propertyAgentService,
                                    PropertySellRepository propertySellRepository,
                                    ModelMapper modelMapper,
                                    AddressService addressService){
        this.propertyAgentService=propertyAgentService;
        this.propertySellRepository=propertySellRepository;
        this.modelMapper=modelMapper;
        this.addressService=addressService;
    }

    @Override
    public PropertySellDTO saveNewProperty(PropertySellDTO propertySellDTO,
                                           String usernamePropertyAgent,
                                           AddressDTO addressDTO) {
        Optional<PropertyAgent> agent = propertyAgentService.findByUsername(usernamePropertyAgent);

        if (agent.isPresent()) {
            Address address =addressService.save(addressDTO);
            PropertySell propertySell = modelMapper.map(propertySellDTO, PropertySell.class);
            propertySell.setPropertyAgent(agent.get());
            
            propertySell.setAddress(address);
            PropertySell savedPropertySell = save(propertySell);
            return modelMapper.map(savedPropertySell, PropertySellDTO.class);
        } else {
            throw new IllegalArgumentException("Agent not found");
        }
    }

    @Override
    public PropertySell save(PropertySell property)
    {
        return propertySellRepository.save(property);
    }

    @Override
    public Optional<PropertySell> findById(Long id)
    {
        return propertySellRepository.findById(id);
    }

    

    

    
}
