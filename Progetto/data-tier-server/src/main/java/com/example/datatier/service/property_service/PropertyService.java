package com.example.datatier.service.property_service;

import java.util.Optional;

import com.example.datatier.dto.AddressDTO;
import com.example.datatier.dto.PropertyDTO;
import com.example.datatier.model.Property;

public interface PropertyService<T extends Property, R extends PropertyDTO> {
    public R saveNewProperty(R propertySellDTO, String usernamePropertyAgent, AddressDTO addressDTO);
                                                    
    public T save(T property);
    public Optional<T> findById(Long id);

}
