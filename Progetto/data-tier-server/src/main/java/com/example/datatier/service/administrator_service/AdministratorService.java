package com.example.datatier.service.administrator_service;





import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datatier.model.Administrator;

import jakarta.transaction.Transactional;

@Service
public class AdministratorService {
    AdministratorAsyncService administratorAsyncService;

    @Autowired
    public AdministratorService(AdministratorAsyncService administratorAsyncService)
    {
        this.administratorAsyncService=administratorAsyncService;
    }

    

    @Transactional
    public CompletableFuture<Administrator> updatePassword(String password, String username)
    {
        return administratorAsyncService.findByUsername(username)
            .thenCompose(administrator -> { //then compose ritorna un nuovo oggetto CompletableFuture
                if(administrator.isPresent()){
                    administrator.get().setPassword(password);
                    return administratorAsyncService.save(administrator.get());
                }
                else
                    throw new IllegalArgumentException("Admin not found");
            })
            .exceptionally(ex -> {
                throw new IllegalArgumentException("Error" + ex.getMessage());
        });
        //thenApply prende il risultato e ci apporta delle modifiche, mentre thenAccept usa l'oggetto senza apportare modifiche
        //entrambi restituiscono un oggetto CompletableFuture, il primo l'oggetto trasformato, mentre thenAccept rappresenta l'azione completato 
    }

    @Transactional
    public CompletableFuture<Administrator> createAdministrator(String username, String usernameNewAdministrator) {
        return administratorAsyncService.findByUsername(username)
            .thenCompose(administrator -> administratorAsyncService.findByUsername(usernameNewAdministrator)
                .thenApply(existingAdmin -> {
                    if (existingAdmin.isPresent()) {
                        throw new IllegalArgumentException("Username already in use");
                    }
                    if (administrator.get().getResponsible() == null) {
                        return generateAdministrator(usernameNewAdministrator, administrator.get());
                    }
                    throw new IllegalArgumentException("Administrator is not authorized to create new administrators");
                }))
            .thenCompose(newAdmin -> administratorAsyncService.save(newAdmin))
            .exceptionally(ex -> {
                throw new IllegalArgumentException("Error: " + ex.getMessage());
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

    @Transactional
    public CompletableFuture<String> getAgencyByUsername(String username)
    {
        return administratorAsyncService.findByUsername(username)
        .thenApply(admin->{
            if(admin.isPresent())
                return admin.get().getAgencyName();
            else
                throw new IllegalArgumentException("Admin not found");

        })
        .exceptionally(ex->{
            throw new IllegalArgumentException("Error"+ex.getMessage());
        });
    }

}
