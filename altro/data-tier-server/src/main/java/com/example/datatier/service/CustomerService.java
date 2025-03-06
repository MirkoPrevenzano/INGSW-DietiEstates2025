package com.example.datatier.service;


import com.example.datatier.dto.UserAuthDTO;
import com.example.datatier.dto.UserDTO;
import com.example.datatier.model.Customer;
import com.example.datatier.model.User;
import com.example.datatier.model.repository.CustomerRepository;
import com.example.datatier.service.auth_service.AuthServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService implements AuthServiceInterface {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final PasswordValidatorService passwordValidatorService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, 
                            ModelMapper modelMapper,
                            PasswordEncoder passwordEncoder, 
                            PasswordValidatorService passwordValidatorService) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidatorService=passwordValidatorService;
    }

    @Override
    public User registrate(UserDTO userDTO) {
        Optional<Customer> user=findByEmail(userDTO.getUsername());
          
        if (user.isPresent()) {
            throw new IllegalArgumentException("Email is present");
        }
        return saveCustomer(userDTO);
    }
            
            

    //verifica validate password

    private User saveCustomer(UserDTO userDTO) {
        Customer customer = modelMapper.map(userDTO, Customer.class);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return save(customer);
    
    }


    @Override
    public User authenticate(UserAuthDTO userAuthDTO) {
        Optional<Customer> user=findByEmail(userAuthDTO.getUsername());
       
        if (user.isPresent()) {
            if (passwordEncoder.matches(userAuthDTO.getPassword(), user.get().getPassword())) {
               return user.get();
            } else {
                throw new IllegalArgumentException("Password wrong");
            }
        } else {
            throw new IllegalArgumentException("Email is not registered");
        }
       
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByUsername(email);
    }

    public Customer save(Customer customer) {
        if (passwordValidatorService.isValid(customer.getPassword())) {
            return customerRepository.save(customer);
        }
        throw new IllegalArgumentException("Invalid password");
    }
}