
package com.dietiestates.backend.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiestates.backend.dto.request.UpdatePasswordDto;
import com.dietiestates.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/users")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COLLABORATOR', 'ROLE_AGENT', 'ROLE_CUSTOMER')")
@RequiredArgsConstructor
@Slf4j
public class UserController 
{
    private final UserService userService;


    
    @PutMapping(path = "/password")
    @Operation(description = "Modifica della password di un utente.",
               tags = "Users")
    @ApiResponses(@ApiResponse(responseCode = "200",
                               description = "Password modificata con successo!"))
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdatePasswordDto updatePasswordDto, 
                                               Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        userService.updatePassword(userDetails.getUsername(), updatePasswordDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .build();
    }   
}