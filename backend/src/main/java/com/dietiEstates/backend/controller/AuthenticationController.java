
package com.dietiEstates.backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dietiEstates.backend.dto.response.AuthenticationResponseDto;
import com.dietiEstates.backend.service.AuthenticationService;

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
    

/*     @PostMapping(path = "/admin-registration")
    public ResponseEntity<?> adminRegistration(@Valid @RequestBody AgencyRegistrationDto aagencyRegistrationDto) 
    {
        authenticationService.adminRegistration(aagencyRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping(path = "customer-registration")
    public ResponseEntity<AuthenticationResponseDto> customerRegistration(@Valid @RequestBody CustomerRegistrationDto userRegistrationDTO) 
    {
        return ResponseEntity.ok().body(authenticationService.customerRegistration(userRegistrationDTO));
    } */

   
    @PostMapping("/login/oauth2/code/google") 
    public ResponseEntity<AuthenticationResponseDto> googleLogin( @RequestBody Map<String, String> request) 
    {
        AuthenticationResponseDto authenticationResponse= authenticationService.authenticateWithGoogle(request); 
        return ResponseEntity.ok(authenticationResponse);
    } 
}