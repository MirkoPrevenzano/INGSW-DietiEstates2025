
package com.dietiestates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiestates.backend.dto.request.CollaboratorCreationDto;
import com.dietiestates.backend.service.AdministratorService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/admins")
@RequiredArgsConstructor
@Slf4j
public class AdministratorController
{
    private final AdministratorService administratorService;


    @PostMapping(path = "/collaborators")
    public ResponseEntity<Void> createCollaborator(@RequestBody CollaboratorCreationDto collaboratorCreationDto, Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        administratorService.createCollaborator(userDetails.getUsername(), collaboratorCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}