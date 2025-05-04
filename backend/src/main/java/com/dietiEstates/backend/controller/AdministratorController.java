
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

import com.dietiEstates.backend.dto.AdminRegistrationDTO;
import com.dietiEstates.backend.dto.OldNewPasswordDTO;
import com.dietiEstates.backend.dto.AgentCustomerRegistrationDTO;
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



    @PostMapping(path = "{username}/create-collaborator")
    public ResponseEntity<?> createCollaborator(@RequestBody AdminRegistrationDTO adminRegistrationDTO) 
    {
        try 
        {
            administratorService.createCollaborator(adminRegistrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.badRequest().header("Error", e.getMessage()).body(null);
        }
    }
    

    @PostMapping(path = "{username}/create-real-estate-agent")
    public ResponseEntity<?> createRealEstateAgent(@PathVariable String username, @RequestBody AgentCustomerRegistrationDTO agentCustomerRegistrationDTO) 
    {
        try 
        {
            administratorService.createRealEstateAgent(username, agentCustomerRegistrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.badRequest().header("Error", e.getMessage()).body(null);
        }
    }


    @PutMapping(path = "{username}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody OldNewPasswordDTO oldNewPasswordDTO) 
    {
        try 
        {
            administratorService.updatePassword(username, oldNewPasswordDTO);
            return ResponseEntity.ok().build();
        } 
/*         catch (UsernameNotFoundException e) 
        {
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        } */
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.badRequest().header("Error", e.getMessage()).body(null);
        }
    }
}