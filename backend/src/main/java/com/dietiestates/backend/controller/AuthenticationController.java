
package com.dietiestates.backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dietiestates.backend.dto.response.AuthenticationResponseDto;
import com.dietiestates.backend.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthenticationController
{
    private final AuthenticationService authenticationService;

    
   
    @PostMapping("/login/oauth2/code/google") 
    @Operation(description = "Autenticazione tramite login oAuth2. Utilizza Google come provider.",
               tags = "Authentication")   
    @SecurityRequirements
    @ApiResponses(@ApiResponse(description = "Login oAuth2 avvenuto con successo!",
                               content = @Content(mediaType = "application/json",
                                                  schema = @Schema(ref = "#components/schemas/jwtTokenResponse"))))
    public ResponseEntity<AuthenticationResponseDto> googleLogin(@RequestBody Map<String, String> request) 
    {
        AuthenticationResponseDto authenticationResponse= authenticationService.authenticateWithGoogle(request); 
        
        return ResponseEntity.status(HttpStatus.OK)
                             .body(authenticationResponse);
    } 
}