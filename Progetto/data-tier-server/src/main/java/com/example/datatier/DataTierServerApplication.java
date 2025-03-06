package com.example.datatier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.example.datatier.controller.AuthenticationResponse;
import com.example.datatier.dto.UserAuthDTO;
import com.example.datatier.dto.UserDTO;

@SpringBootApplication
public class DataTierServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataTierServerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        testRegister();
    }

    //tentativo di autenticazione: OK
    public void testRegister() {
        /*RestTemplate restTemplate = new RestTemplate();
        // Crea l'header della richiesta
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");


        // Crea un UserAuthDTO di esempio per l'autenticazione
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername("testuser@example.com");
        userAuthDTO.setPassword("pasfsword123");
        userAuthDTO.setRole("customer");

        // Crea l'entity della richiesta per l'autenticazione
        HttpEntity<UserAuthDTO> authRequest = new HttpEntity<>(userAuthDTO, headers);

        // Invia la richiesta POST al metodo /auth/authenticate
        ResponseEntity<AuthenticationResponse> authResponse = restTemplate.exchange(
                "http://localhost:8080/auth/authenticate",
                HttpMethod.POST,
                authRequest,
                AuthenticationResponse.class
        );

        // Verifica che la risposta di autenticazione sia OK
        if (authResponse.getStatusCode().is2xxSuccessful() && authResponse.getBody() != null) {
            System.out.println("Authentication successful. Token: " + authResponse.getBody().getToken());
        } else {
            System.out.println("Authentication failed. Status code: " + authResponse.getStatusCode());
        }*/
    }
    //Tentativo di registrazione customer: OK

    /*

    public void testRegister() {
        RestTemplate restTemplate = new RestTemplate();

        // Crea un UserDTO di esempio
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser@example3.com");
        userDTO.setPassword("password123");
        userDTO.setName("Mario");
        userDTO.setSurname("Rossi");

        // Crea l'header della richiesta
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Crea l'entity della richiesta
        HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

        // Invia la richiesta POST al metodo /auth/register
        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
                "http://localhost:8080/auth/register",
                HttpMethod.POST,
                request,
                AuthenticationResponse.class
        );

        // Verifica che la risposta sia OK
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            System.out.println("Registration successful. Token: " + response.getBody().getToken());
        } else {
            System.out.println("Registration failed. Status code: " + response.getStatusCode());
        }
    }*/
}

    /*@Bean
    public CommandLineRunner run(PropertyRentService propertyRentService
                                 ) {
        return args -> {
            // Crea un amministratore di esempio
            

            // Salva l'amministratore
            

                            // Crea un DTO di esempio per PropertyRent
                            PropertyRentDTO propertyRentDTO = new PropertyRentDTO();
                            propertyRentDTO.setDescription("Test Property");
                            propertyRentDTO.setSizeInSquareMeters(100.0);
                            propertyRentDTO.setRoomCount(3);
                            propertyRentDTO.setFloorNumber(2);
                            propertyRentDTO.setEnergyClass("A");
                            propertyRentDTO.setNumberParkingSpace(1);
                            propertyRentDTO.setMonthlyRent(1200.0);
                            propertyRentDTO.setSecurityDeposit(2400.0);
                            propertyRentDTO.setContractYears(1);
                            propertyRentDTO.setCondoFee(100.0);
							
							AddressDTO addressDTO=new AddressDTO();
							addressDTO.setCity("napoli");
							addressDTO.setCap("80125");
							addressDTO.setHouseNumber(2);
							addressDTO.setStreet("via g.marino");
							addressDTO.setProvince("Na");
							addressDTO.setRegion("Campania");

                            // Esegui il metodo del servizio per salvare una nuova proprietà in affitto
                            propertyRentService.saveNewProperty(propertyRentDTO, "agentUsername", addressDTO);
                            


                            /*************************************************************************** */
                             // Crea un UserDTO di esempio
           
