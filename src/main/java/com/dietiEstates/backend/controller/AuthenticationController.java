
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.AuthenticationResponseDTO;
import com.dietiEstates.backend.dto.UserDTO;
import com.dietiEstates.backend.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController
{
    private final AuthenticationService authenticationService;

    

    @PostMapping(path = "standard-registration")
    public ResponseEntity<AuthenticationResponseDTO> standardRegistration(@RequestBody UserDTO userDTO) 
    {
        try 
        {
            return ResponseEntity.ok().body(authenticationService.standardRegistration(userDTO));
        } 
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.badRequest().header("Error", e.getMessage())
                                              .header("ACCESS_CONTROL_EXPOSE_HEADER", "Error").body(null);
        }
    }

    @PostMapping("google-registration")
    public String googleRegistration(@RequestBody String entity) 
    {
        //TODO: process POST request
        return entity;
    }

    @PostMapping("google-login")
    public String googleLogin(@RequestBody String entity) 
    {
        //TODO: process POST request
        return entity;
    }
}