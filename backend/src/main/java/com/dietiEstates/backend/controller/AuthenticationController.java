
package com.dietiEstates.backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dietiEstates.backend.dto.request.AdminRegistrationDTO;
import com.dietiEstates.backend.dto.request.CustomerRegistrationDTO;
import com.dietiEstates.backend.dto.response.AuthenticationResponseDTO;
import com.dietiEstates.backend.service.AuthenticationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

    

    @PostMapping(path = "/admin-registration/{prova}")
    public ResponseEntity<?> adminRegistration(@Valid @RequestBody AdminRegistrationDTO adminRegistrationDTO, @Min(3) @PathVariable Integer prova) 
    {
        try 
        {
            authenticationService.adminRegistration(adminRegistrationDTO);
            log.info("Admin (and agency) registered successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", e.getMessage()).build();
        }
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error", e.getMessage()).build();
        }
    }


    @PostMapping(path = "standard-registration")
    public ResponseEntity<AuthenticationResponseDTO> customerRegistration(@Valid @RequestBody CustomerRegistrationDTO userRegistrationDTO) 
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