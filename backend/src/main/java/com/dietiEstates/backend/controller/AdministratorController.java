
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dietiEstates.backend.dto.request.CollaboratorRegistrationDTO;
import com.dietiEstates.backend.dto.request.AdminRegistrationDTO;
import com.dietiEstates.backend.dto.request.AgentRegistrationDTO;
import com.dietiEstates.backend.dto.request.UpdatePasswordDTO;
import com.dietiEstates.backend.service.AdministratorService;
import com.dietiEstates.backend.service.mail.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdministratorController
{
    private final EmailService emailService;
    private final AdministratorService administratorService;
    


/*     @PostMapping(path = "/testmail")
    public ResponseEntity<Void> testmail() 
    {
        emailService.sendAgentRegistrationEmail("ciropizza2002@gmail.com");
        return ResponseEntity.ok().build();
    } */


    @PostMapping(path = "/{username}/create-collaborator")
    public ResponseEntity<Void> createCollaborator(@PathVariable String username, @RequestBody CollaboratorRegistrationDTO collaboratorRegistrationDTO) 
    {
        try 
        {
            administratorService.createCollaborator(username, collaboratorRegistrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", e.getMessage()).build();
        }
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error", e.getMessage()).build();
        }
    }
    

    @PostMapping(path = "/{username}/create-agent")
    public ResponseEntity<Void> createAgent(@PathVariable String username, @RequestBody AgentRegistrationDTO agentRegistrationDTO) 
    {
        try 
        {
            administratorService.createAgent(username, agentRegistrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", e.getMessage()).build();
        }
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error", e.getMessage()).build();
        }
    }


    @PutMapping(path = "/{username}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody UpdatePasswordDTO updatePasswordDTO) 
    {
        try 
        {
            administratorService.updatePassword(username, updatePasswordDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } 
        catch (UsernameNotFoundException e) 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", e.getMessage()).build();
        }
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error", e.getMessage()).build();
        }
    }
}