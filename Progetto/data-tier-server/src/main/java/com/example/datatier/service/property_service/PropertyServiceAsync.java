package com.example.datatier.service.property_service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.datatier.model.Property;


public interface PropertyServiceAsync<T extends Property> {
    public CompletableFuture<T> save(T property);
    public CompletableFuture<Optional<T>> findById(Long id);
}
