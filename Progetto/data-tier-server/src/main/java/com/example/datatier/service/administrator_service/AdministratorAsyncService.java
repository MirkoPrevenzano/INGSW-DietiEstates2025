package com.example.datatier.service.administrator_service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.model.Administrator;
import com.example.datatier.model.repository.AdministratorRepository;
import com.example.datatier.service.PasswordValidatorService;

@Service
public class AdministratorAsyncService {
     AdministratorRepository administratorRepository;
    PasswordValidatorService passwordValidator;

    @Autowired
    public AdministratorAsyncService(AdministratorRepository administratorRepository, 
                                    PasswordValidatorService passwordValidator)
    {
        this.administratorRepository=administratorRepository;
        this.passwordValidator=passwordValidator;
    }
    @Async
    public CompletableFuture<Administrator> save(Administrator administrator)
    {
        if(passwordValidator.isValid(administrator.getPassword()))
            return CompletableFuture.completedFuture(administratorRepository.save(administrator));
        throw new IllegalArgumentException("Password invalida");
    }

    @Async
    public CompletableFuture<Optional<Administrator>> findByUsername(String username)
    {
        return CompletableFuture.completedFuture(administratorRepository.findByUsername(username));
    }


}
