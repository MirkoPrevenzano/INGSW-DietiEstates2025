package com.example.datatier.service;



import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.datatier.model.Administrator;
import com.example.datatier.repository.AdministratorRepository;

import jakarta.transaction.Transactional;

@Service
public class AdministratorService {
    AdministratorRepository administratorRepository;
    PasswordValidatorService passwordValidator;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository, 
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
    public CompletableFuture<Administrator> findByUsername(String username)
    {
        return CompletableFuture.completedFuture(administratorRepository.findByUsername(username));
    }

    @Transactional
    public CompletableFuture<Administrator> updatePassword(String password, String username)
    {
        return findByUsername(username)
            .thenCompose(administrator -> { //then compose ritorna un nuovo oggetto CompletableFuture
                administrator.setPassword(password);
                return save(administrator);})
            .exceptionally(ex -> {
                throw new RuntimeException("Error" + ex.getMessage());
        });
        //thenApply prende il risultato e ci apporta delle modifiche, mentre thenAccept usa l'oggetto senza apportare modifiche
        //entrambi restituiscono un oggetto CompletableFuture, il primo l'oggetto trasformato, mentre thenAccept rappresenta l'azione completata

       /* Administrator administrator= findByUsername(username);
        administrator.setPassword(password);
        save(administrator);*/
        
       
    }

    @Async
    @Transactional
    public CompletableFuture<Administrator> createAdministrator(String username, String usernameNewAdministrator) {
        // Recupera l'amministratore esistente
        return findByUsername(username) //uso thenCompose quando all'interno è gestita un'altra chiamata asincrona
            .thenCompose(administrator -> findByUsername(usernameNewAdministrator)
                .thenApply(existingAdmin -> {//prendo il risultato dell'operazione asincrna ed effettuo delle operazioni intermedie
                    if (existingAdmin != null) 
                        throw new IllegalArgumentException("Username already in use");
                    if (administrator.getResponsible() == null) 
                        return generateAdministrator(usernameNewAdministrator, administrator);
                    throw new IllegalArgumentException("Administrator is not authorized to create new administrators");
                }))//se le operazioni vanno correttamente chiamo di nuovo thenCompose per ritornare un altro oggetto CompletableFuture
            .thenCompose(newAdmin -> save(newAdmin)) 
            .exceptionally(ex -> {
                throw new RuntimeException("Error" + ex.getMessage());
        });
    }


    private Administrator generateAdministrator(String username,Administrator administrator) {
        Administrator newAdministrator = new Administrator();
        newAdministrator.setUsername(username);
        newAdministrator.setAgencyName(administrator.getAgencyName());
        String passwordDefault="Password123.";
        newAdministrator.setPassword(passwordDefault);
        newAdministrator.setResponsible(administrator);
        administrator.addManager(newAdministrator);
        
        return newAdministrator;
    }

}
