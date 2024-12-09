package com.example.datatier.service.property_service;

import java.util.concurrent.CompletableFuture;

import com.example.datatier.dto.AddressDTO;
import com.example.datatier.dto.PropertyDTO;

public interface PropertyService<T extends PropertyDTO> {
    public CompletableFuture<T> saveNewProperty(T propertyDTO,
                                                    String usernamePropertyAgent,
                                                    AddressDTO addressDTO);

}
