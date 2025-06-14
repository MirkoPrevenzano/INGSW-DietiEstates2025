
package com.dietiEstates.backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dietiEstates.backend.dto.request.CustomerRegistrationDTO;
import com.dietiEstates.backend.dto.response.AuthenticationResponseDTO;
import com.dietiEstates.backend.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController
{
    private final AuthenticationService authenticationService;

    

    @PostMapping(path = "customer-registration")
    public ResponseEntity<AuthenticationResponseDTO> customerRegistration(@RequestBody CustomerRegistrationDTO userRegistrationDTO) 
    {
        try 
        {
            return ResponseEntity.ok().body(authenticationService.customerRegistration(userRegistrationDTO));
        } 
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error", e.getMessage()).build();
        }
    }

   
    @PostMapping("/login/oauth2/code/google") 
    public ResponseEntity<AuthenticationResponseDTO> googleLogin( @RequestBody Map<String, String> request) { 
        try{
            AuthenticationResponseDTO authenticationResponse= authenticationService.authenticateWithGoogle(request); 
            return ResponseEntity.ok(authenticationResponse);
        } 
        catch(Exception e){
            return ResponseEntity.badRequest().build(); 
        }

    } 
}