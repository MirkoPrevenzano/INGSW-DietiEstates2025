package com.example.datatier.service.customer_service;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.datatier.dto.CustomerDTO;
import com.example.datatier.model.Customer;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    private final CustomerAsyncService customerAsyncService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerAsyncService customerAsyncService,
                            ModelMapper modelMapper,
                            PasswordEncoder passwordEncoder )
    {
        this.customerAsyncService=customerAsyncService;
        this.modelMapper=modelMapper;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional
    public CompletableFuture<Customer> save(CustomerDTO customerDTO)
    {
        return customerAsyncService.findByEmail(customerDTO.getUsername())
        .thenCompose(user->{
            if(user.isPresent())
                throw new IllegalArgumentException("Email is present");
            Customer customer=modelMapper.map(customerDTO, Customer.class);
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            return customerAsyncService.save(customer);
        })
        .exceptionally(ex-> {
            throw new IllegalArgumentException("error"+ex.getMessage());
        });
    }
}
