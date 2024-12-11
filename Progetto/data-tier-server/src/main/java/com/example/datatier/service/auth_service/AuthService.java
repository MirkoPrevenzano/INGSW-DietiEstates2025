package com.example.datatier.service.auth_service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datatier.config.JwtService;
import com.example.datatier.controller.AuthenticationResponse;
import com.example.datatier.dto.UserAuthDTO;
import com.example.datatier.dto.UserDTO;
import com.example.datatier.model.User;
import com.example.datatier.service.CustomerService;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthServiceFactory authServiceFactory;
    private final CustomerService customerService;
    


    @Autowired
    public AuthService( JwtService jwtService,
                        AuthServiceFactory authServiceFactory,
                        CustomerService customerService)
    {
        this.jwtService=jwtService;
        this.authServiceFactory=authServiceFactory;
        this.customerService=customerService;
        
    }

    public AuthenticationResponse register(UserDTO request) {
    
        User user= customerService.registrate(request);
        
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    
    }
    

    public AuthenticationResponse authenticate(UserAuthDTO request) {
        AuthServiceInterface authService = authServiceFactory.getAuthService(request.getRole());
        User user= authService.authenticate(request);
      
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
        
    }


}
