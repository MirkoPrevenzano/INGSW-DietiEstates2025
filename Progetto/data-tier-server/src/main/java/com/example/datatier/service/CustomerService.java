package com.example.datatier.service;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.model.Customer;
import com.example.datatier.repository.CustomerRepository;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository=customerRepository;
    }

    @Async
    public CompletableFuture<Optional<Customer>> findByEmail(String email)
    {
        return CompletableFuture.completedFuture(customerRepository.findByEmail(email));
    }

}