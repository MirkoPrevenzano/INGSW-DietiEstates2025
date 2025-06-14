
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

import com.dietiEstates.backend.dto.UserRegistrationDTO;
import com.dietiEstates.backend.dto.OldNewPasswordDTO;
import com.dietiEstates.backend.service.AdministratorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdministratorController
{
    private final AdministratorService administratorService;



    @PostMapping(path = "/create-collaborator")
    public ResponseEntity<?> createCollaborator(@RequestBody UserRegistrationDTO userRegistrationDTO) 
    {
        try 
        {
            administratorService.createCollaborator(userRegistrationDTO);
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
    

    @PostMapping(path = "/{username}/create-real-estate-agent")
    public ResponseEntity<?> createAgent(@PathVariable String username, @RequestBody UserRegistrationDTO userRegistrationDTO) 
    {
        try 
        {
            administratorService.createAgent(username, userRegistrationDTO);
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
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody OldNewPasswordDTO oldNewPasswordDTO) 
    {
        try 
        {
            administratorService.updatePassword(username, oldNewPasswordDTO);
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