
package com.dietiestates.backend.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

import com.dietiestates.backend.dto.request.CustomerRegistrationDto;
import com.dietiestates.backend.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController 
{
    private final CustomerService customerService;


    @PostMapping
    @Operation(description = "Registrazione di un nuovo cliente all'interno dell'applicazione.",
               tags = "Customers")
    @SecurityRequirements
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Cliente creato con successo!")})
    public ResponseEntity<Void> customerRegistration(@Valid @RequestBody CustomerRegistrationDto userRegistrationDTO) 
    {
        customerService.customerRegistration(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    } 
}