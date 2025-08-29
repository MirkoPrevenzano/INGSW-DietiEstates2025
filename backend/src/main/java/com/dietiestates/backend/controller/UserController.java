
package com.dietiestates.backend.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.dietiestates.backend.dto.request.UpdatePasswordDto;
import com.dietiestates.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController 
{
    private final UserService userService;

    
    @PutMapping(path = "/password")
    @Operation(summary = "", 
               description = "",
               tags = "Admins")
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Collaboratore creato!",
                                ref = ""),
                   @ApiResponse(responseCode = "500",
                                description = "Errore interno non gestito",
                                ref = "")})
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto, Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        userService.updatePassword(userDetails.getUsername(), updatePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }   
}