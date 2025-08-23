
package com.dietiestates.backend.controller;

import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiestates.backend.dto.request.CustomerRegistrationDto;
import com.dietiestates.backend.dto.response.AuthenticationResponseDto;
import com.dietiestates.backend.service.AuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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


    @PostMapping(path = "customer-registration")
    public ResponseEntity<AuthenticationResponseDto> customerRegistration(@Valid @RequestBody CustomerRegistrationDto userRegistrationDTO) 
    {
        return ResponseEntity.ok().body(authenticationService.customerRegistration(userRegistrationDTO));
    } 
   
    @PostMapping("/login/oauth2/code/google") 
    public ResponseEntity<AuthenticationResponseDto> googleLogin(@RequestBody Map<String, String> request) 
    {
        AuthenticationResponseDto authenticationResponse= authenticationService.authenticateWithGoogle(request); 
        return ResponseEntity.ok(authenticationResponse);
    } 
}