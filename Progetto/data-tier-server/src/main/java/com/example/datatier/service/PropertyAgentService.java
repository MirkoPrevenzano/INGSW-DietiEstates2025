package com.example.datatier.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.datatier.dto.UserAuthDTO;
import com.example.datatier.dto.UserDTO;
import com.example.datatier.model.Administrator;
import com.example.datatier.model.PropertyAgent;
import com.example.datatier.model.User;
import com.example.datatier.model.repository.PropertyAgentRepository;
import com.example.datatier.service.auth_service.AuthServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class PropertyAgentService implements AuthServiceInterface {

    private final PropertyAgentRepository propertyAgentRepository;
    private final PasswordValidatorService passwordValidatorService;
    private final AdministratorService administratorService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Autowired
    public PropertyAgentService(PropertyAgentRepository propertyAgentRepository,
                                PasswordValidatorService passwordValidatorService,
                                AdministratorService administratorService,
                                PasswordEncoder passwordEncoder,
                                ModelMapper modelMapper
                                ){
        this.propertyAgentRepository = propertyAgentRepository;
        this.passwordValidatorService = passwordValidatorService;
        this.administratorService = administratorService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper=modelMapper;
    }

    public Optional<PropertyAgent> findByUsername(String username) {
        return propertyAgentRepository.findByUsername(username);
    }

    public PropertyAgent save(PropertyAgent propertyAgent) {
        if (passwordValidatorService.isValid(propertyAgent.getPassword())) {
            propertyAgent.setPassword(passwordEncoder.encode(propertyAgent.getPassword()));
            return propertyAgentRepository.save(propertyAgent);
        }
        throw new IllegalArgumentException("Invalid password");
    }

    @Transactional
    public PropertyAgent saveNewAgent(String username, String password, String usernameAdmin) {
        Optional<PropertyAgent> propertyAgent= findByUsername(username);
            
        if (propertyAgent.isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }
        Optional<Administrator> admin=administratorService.findByUsername(usernameAdmin);  
        if (admin.isEmpty()) {
            throw new IllegalArgumentException("Administrator not found");
        }
        if (passwordValidatorService.isValid(password)) {
            PropertyAgent propertyAgentNew = generatePropertyAgent(admin.get(), username, password);
            return save(propertyAgentNew);
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }


    private PropertyAgent generatePropertyAgent(Administrator admin, String username, String password) {
        PropertyAgent propertyAgent = new PropertyAgent();
        propertyAgent.setAdministrator(admin);
        propertyAgent.setPassword(password);
        propertyAgent.setUsername(username);
        return propertyAgent;
    }

    @Override
    public User authenticate(UserAuthDTO userAuthDTO) {
        Optional<PropertyAgent> propertyAgent= findByUsername(userAuthDTO.getUsername());
            
        if (propertyAgent.isPresent()) {
            PropertyAgent agent = propertyAgent.get();
            if (passwordEncoder.matches(userAuthDTO.getPassword(), agent.getPassword())) {
                return modelMapper.map(agent,User.class);
            } else {
                throw new IllegalArgumentException("Password wrong");
            }
        } else {
                throw new IllegalArgumentException("Email is not registered");
        }
    
    }

    @Override
    public User registrate(UserDTO userDTO) {
        return saveNewAgent(userDTO.getUsername(), userDTO.getPassword(), userDTO.getUsername());
            
    }
}