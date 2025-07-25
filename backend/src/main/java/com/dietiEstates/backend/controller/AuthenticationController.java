
package com.dietiEstates.backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dietiEstates.backend.dto.request.AdminRegistrationDTO;
import com.dietiEstates.backend.dto.request.CustomerRegistrationDTO;
import com.dietiEstates.backend.dto.response.AuthenticationResponseDTO;
import com.dietiEstates.backend.service.AuthenticationService;

import jakarta.validation.Valid;
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
    

    @PostMapping(path = "/admin-registration")
    public ResponseEntity<?> adminRegistration(@Valid @RequestBody AdminRegistrationDTO adminRegistrationDTO) 
    {
        authenticationService.adminRegistration(adminRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping(path = "customer-registration")
    public ResponseEntity<AuthenticationResponseDTO> customerRegistration(@Valid @RequestBody CustomerRegistrationDTO userRegistrationDTO) 
    {
        return ResponseEntity.ok().body(authenticationService.customerRegistration(userRegistrationDTO));
    }

   
    @PostMapping("/login/oauth2/code/google") 
    public ResponseEntity<AuthenticationResponseDTO> googleLogin( @RequestBody Map<String, String> request) 
    {
        AuthenticationResponseDTO authenticationResponse= authenticationService.authenticateWithGoogle(request); 
        return ResponseEntity.ok(authenticationResponse);
    } 
}