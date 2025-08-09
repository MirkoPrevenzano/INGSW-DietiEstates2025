
package com.dietiEstates.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.request.CustomerRegistrationDTO;
import com.dietiEstates.backend.exception.EmailServiceException;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.service.mail.CustomerWelcomeEmailService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;


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
    public void customerRegistration(CustomerRegistrationDTO customerRegistrationDTO) throws IllegalArgumentException, MappingException
    {
        if(customerRepository.findByUsername(customerRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This e-mail is already present!");
            throw new IllegalArgumentException("This e-mail is already present!");
        }

        Customer customer = modelMapper.map(customerRegistrationDTO, Customer.class);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer = customerRepository.save(customer);

        log.info("Customer was registrated successfully!");

        try 
        {
            customerWelcomeEmailService.sendWelcomeEmail(customer);
        } 
        catch (EmailServiceException e) 
        {
            log.warn(e.getMessage());
        }
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
