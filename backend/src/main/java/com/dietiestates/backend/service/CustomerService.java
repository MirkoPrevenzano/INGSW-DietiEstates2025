
package com.dietiestates.backend.service;

import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietiestates.backend.dto.request.CustomerRegistrationDto;
import com.dietiestates.backend.model.entity.Customer;
import com.dietiestates.backend.repository.CustomerRepository;
import com.dietiestates.backend.service.mail.CustomerWelcomeEmailService;

import org.modelmapper.ModelMapper;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CustomerService 
{
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final CustomerWelcomeEmailService customerWelcomeEmailService;

    
    
    @Transactional
    public void customerRegistration(CustomerRegistrationDto customerRegistrationDto) throws IllegalArgumentException
    {
        if(customerRepository.findByUsername(customerRegistrationDto.getUsername()).isPresent())
        {
            log.error("This customer's email is already present!");
            throw new IllegalArgumentException("This customer's email is already present!");
        }

        Customer customer = modelMapper.map(customerRegistrationDto, Customer.class);

        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);

        customer = customerRepository.save(customer);

        log.info("Customer was registrated successfully!");
        
        customerWelcomeEmailService.sendWelcomeEmail(customer);
    }



    /* 
    *Vedo se esiste l'utente, altrimenti lo genero e restituisco al authService l'oggetto User 
    */ 
    public Customer authenticateWithExternalAPI(GoogleIdToken.Payload reqUser) {
        String email = reqUser.getEmail();
        log.info("Autenticazione con API esterna per l'email: {}", email);
    
        // Cerca l'utente nel database
        Optional<Customer> optionalUser = customerRepository.findByUsername(email);
    
        // Se l'utente esiste, restituiscilo
        if (optionalUser.isPresent()) {
            log.info("Utente trovato nel database: {}", email);
            return optionalUser.get();
        }
    
        // Altrimenti, genera un nuovo utente
        log.info("Utente non trovato, creazione di un nuovo utente per l'email: {}", email);
        return generateCustomerAPI(email, reqUser);
    }

    /*
    *Dal payload google ottengo diverse informazioni, che vado ad inserire nell'oggetto che si sta creando 
    *Decido di mettere una password di default visto che il campo non può essere null 
    */ 
    private Customer generateCustomerAPI(String email, GoogleIdToken.Payload reqUser) {
        Customer customer = new Customer();
        customer.setName(reqUser.get("given_name").toString());
        customer.setPassword(passwordEncoder.encode(generateRandomPassword())); // Genera una password casuale
        customer.setUsername(email);
        customer.setSurname(reqUser.get("family_name").toString());
        customer.setAuthWithExternalAPI(true);
        log.info("Nuovo utente creato con email: {}", email);
        return customerRepository.save(customer);
    }
    
    private String generateRandomPassword() {
        // Genera una password casuale di 12 caratteri
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}
