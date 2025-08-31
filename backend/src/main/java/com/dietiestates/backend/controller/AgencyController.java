
package com.dietiestates.backend.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiestates.backend.dto.request.AgencyRegistrationDto;
import com.dietiestates.backend.service.AgencyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agencies")
@RequiredArgsConstructor
@Slf4j
public class AgencyController
{
    private final AgencyService agencyService;
    

    //@PreAuthorize("sd")
    @PostMapping
    @Operation(description = "Registrazione di una nuova agenzia (con incluso l'amministratore della stessa agenzia) all'interno dell'applicazione.",
               tags = "Agencies")
    @SecurityRequirements
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Registrazione dell'agenzia (con relativo amministratore) completata con successo!")})
    public ResponseEntity<Void> createAgency(@Valid @RequestBody AgencyRegistrationDto aagencyRegistrationDto) 
    {
        agencyService.createAgency(aagencyRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}