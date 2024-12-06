package com.example.datatier.service.property_service.property_rent_service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.model.PropertyRent;
import com.example.datatier.model.repository.PropertyRentRepository;
import com.example.datatier.service.property_service.PropertyServiceAsync;

@Service
public class PropertyRentAsyncService implements PropertyServiceAsync<PropertyRent> {
     PropertyRentRepository propertyRentRepository;

    @Autowired
    public PropertyRentAsyncService( PropertyRentRepository propertyRentRepository){
        this.propertyRentRepository=propertyRentRepository;
    }

    @Override
    @Async
    public CompletableFuture<PropertyRent> save(PropertyRent propertyRent)
    {
        return CompletableFuture.completedFuture(propertyRentRepository.save(propertyRent));
    }

    @Override
    @Async 
    public CompletableFuture<Optional<PropertyRent>> findById(Long id)
    {
        return CompletableFuture.completedFuture(propertyRentRepository.findById(id));
    }


}
