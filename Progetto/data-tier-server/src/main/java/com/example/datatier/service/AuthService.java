package com.example.datatier.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.datatier.config.JwtService;
import com.example.datatier.controller.AuthenticationResponse;
import com.example.datatier.dto.UserAuthDTO;
import com.example.datatier.dto.CustomerDTO;
import com.example.datatier.model.Customer;
import com.example.datatier.service.customer_service.CustomerService;

@Service
public class AuthService {
    private final CustomerService customerService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;


    @Autowired
    public AuthService(ModelMapper modelMapper,
                        CustomerService customerService,
                        JwtService jwtService)
    {
        this.modelMapper=modelMapper;
        this.customerService=customerService;
        this.jwtService=jwtService;
        
    }

    public AuthenticationResponse register(CustomerDTO request) {
       customerService.save(request);
       Customer customer=modelMapper.map(request, Customer.class);
       String jwtToken= jwtService.generateToken(customer);
       return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(UserAuthDTO request) {
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }


}
