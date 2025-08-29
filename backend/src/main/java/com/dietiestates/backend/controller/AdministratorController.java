
package com.dietiestates.backend.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiestates.backend.dto.request.CollaboratorCreationDto;
import com.dietiestates.backend.service.AdministratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    @Operation(description = "Creazione di un account per un nuovo collaboratore dell'admin.",
               tags = "Admins")
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Collaboratore creato!",
                                ref = ""),
                   @ApiResponse(responseCode = "500",
                                description = "Errore interno non gestito",
                                ref = "")})
    public ResponseEntity<Void> createCollaborator(@Valid @RequestBody CollaboratorCreationDto collaboratorCreationDto, Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        administratorService.createCollaborator(userDetails.getUsername(), collaboratorCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}