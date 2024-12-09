package com.example.datatier.service.customer_service;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.model.Customer;
import com.example.datatier.model.repository.CustomerRepository;
import com.example.datatier.service.PasswordValidatorService;


@Service
public class CustomerAsyncService {
    private final CustomerRepository customerRepository;
    private final PasswordValidatorService passwordValidatorService;

    @Autowired
    public CustomerAsyncService(CustomerRepository customerRepository,
                                PasswordValidatorService passwordValidatorService)
    {
        this.customerRepository=customerRepository;
        this.passwordValidatorService=passwordValidatorService;
    }

    @Async
    public CompletableFuture<Optional<Customer>> findByEmail(String email)
    {
        return CompletableFuture.completedFuture(customerRepository.findByEmail(email));
    }

    @Async
    public CompletableFuture<Customer> save(Customer customer)
    {
        if(passwordValidatorService.isValid(customer.getPassword()))
            return CompletableFuture.completedFuture(customerRepository.save(customer));
        throw new IllegalArgumentException("Invalid password");
    }

}