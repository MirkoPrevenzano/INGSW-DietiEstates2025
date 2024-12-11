package com.example.datatier.service.auth_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.datatier.service.AdministratorService;
import com.example.datatier.service.CustomerService;
import com.example.datatier.service.PropertyAgentService;

@Component
public class AuthServiceFactory {

    private final CustomerService customerService;
    private final AdministratorService administratorAuthService;
    private final PropertyAgentService propertyAgentService;

    @Autowired
    public AuthServiceFactory(CustomerService customerService,
                              AdministratorService administratorAuthService,
                              PropertyAgentService propertyAgentService) {
        this.customerService = customerService;
        this.administratorAuthService = administratorAuthService;
        this.propertyAgentService = propertyAgentService;
    }

    public AuthServiceInterface getAuthService(String userType) {
        switch (userType.toLowerCase()) {
            case "customer":
                return customerService;
            case "administrator":
                return administratorAuthService;
            case "propertyagent":
                return propertyAgentService;
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }
}