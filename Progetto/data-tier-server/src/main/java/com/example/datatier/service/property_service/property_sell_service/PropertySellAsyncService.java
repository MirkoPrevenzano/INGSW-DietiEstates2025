package com.example.datatier.service.property_service.property_sell_service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.model.PropertySell;
import com.example.datatier.model.repository.PropertySellRepository;
import com.example.datatier.service.property_service.PropertyServiceAsync;

@Service
public class PropertySellAsyncService implements PropertyServiceAsync<PropertySell>{
    PropertySellRepository propertySellRepository;

    @Autowired
    public PropertySellAsyncService(PropertySellRepository propertySellRepository)
    {
        this.propertySellRepository=propertySellRepository;
    }

    @Override
    @Async
    public CompletableFuture<PropertySell> save(PropertySell property)
    {
        return CompletableFuture.completedFuture(propertySellRepository.save(property));
    }

    @Override
    @Async
    public CompletableFuture<Optional<PropertySell>> findById(Long id)
    {
        return CompletableFuture.completedFuture(propertySellRepository.findById(id));
    }
}
