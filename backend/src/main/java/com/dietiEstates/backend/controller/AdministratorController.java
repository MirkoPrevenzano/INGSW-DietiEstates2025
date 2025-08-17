
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dietiEstates.backend.dto.request.CollaboratorCreationDto;
import com.dietiEstates.backend.dto.request.AgentCreationDto;
import com.dietiEstates.backend.dto.request.UpdatePasswordDto;
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
    public ResponseEntity<Void> createCollaborator(@PathVariable String username, @RequestBody CollaboratorCreationDto collaboratorCreationDto) 
    {
        administratorService.createCollaborator(username, collaboratorCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


/*     @PostMapping(path = "/{username}/create-agent")
    public ResponseEntity<Void> createAgent(@PathVariable String username, @RequestBody AgentCreationDto agentCreationDto) 
    {
        administratorService.createAgent(username, agentCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
     */
    
    @PutMapping(path = "/{username}/update-password")
    public ResponseEntity<Void> updatePassword(@PathVariable String username, @RequestBody UpdatePasswordDto updatePasswordDto) 
    {
        userService.updatePassword(username, updatePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}