package com.example.datatier.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.datatier.dto.UserDTO;
import com.example.datatier.service.auth_service.AuthService;
import com.example.datatier.dto.UserAuthDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService)
    {
        this.authService=authService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDTO request)
    {
        AuthenticationResponse authenticationResponse= authService.register(request);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> autenticate(@RequestBody UserAuthDTO request)
    {
        AuthenticationResponse authenticationResponse= authService.authenticate(request);
        return ResponseEntity.ok(authenticationResponse);
    }
}
