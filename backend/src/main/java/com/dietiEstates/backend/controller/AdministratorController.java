
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dietiEstates.backend.dto.request.CollaboratorRegistrationDTO;
import com.dietiEstates.backend.dto.request.AgentRegistrationDTO;
import com.dietiEstates.backend.dto.request.UpdatePasswordDTO;
import com.dietiEstates.backend.service.AdministratorService;
import com.dietiEstates.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdministratorController
{
    private final AdministratorService administratorService;
    private final UserService userService;
    

    @PostMapping(path = "/{username}/create-collaborator")
    public ResponseEntity<Void> createCollaborator(@PathVariable String username, @RequestBody CollaboratorRegistrationDTO collaboratorRegistrationDTO) 
    {
        administratorService.createCollaborator(username, collaboratorRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping(path = "/{username}/create-agent")
    public ResponseEntity<Void> createAgent(@PathVariable String username, @RequestBody AgentRegistrationDTO agentRegistrationDTO) 
    {
        administratorService.createAgent(username, agentRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    
    @PutMapping(path = "/{username}/update-password")
    public ResponseEntity<Void> updatePassword(@PathVariable String username, @RequestBody UpdatePasswordDTO updatePasswordDTO) 
    {
        userService.updatePassword(username, updatePasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}