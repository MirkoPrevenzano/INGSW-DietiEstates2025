
package com.dietiEstates.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.dietiEstates.backend.dto.AddressDTO;
import com.dietiEstates.backend.dto.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.extra.RealEstateBooleanFeatures;
import com.dietiEstates.backend.extra.RealEstateLocationFeatures;
import com.dietiEstates.backend.extra.RealEstateMainFeatures;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.util.JFreeChartUtil;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService 
{

    private final JFreeChartUtil jFreeChartUtil;
    private final RealEstateRepository realEstateRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    public RealEstateCreationDTO viewRealEstate(String username, Long realEstateId) 
    {
        Customer customer = customerRepository.findByUsername(username).get();
        RealEstate realEstate = realEstateRepository.findById(realEstateId).get();
           
        RealEstateCreationDTO realEstateCreationDTO = realEstateCreationDTOMapper(realEstate);
                

        
                long newViewsNumber = realEstate.getRealEstateStats().getViewsNumber() + 1;
                realEstate.getRealEstateStats().setViewsNumber(newViewsNumber);
        
                CustomerViewsRealEstate customerViewsRealEstate = new CustomerViewsRealEstate(
                                                                        new CustomerViewsRealEstateId(customer.getUserId(), realEstate.getRealEstateId()), 
                                                                        LocalDateTime.now(), 
                                                                        customer,
                                                                        realEstate);
        
                List<CustomerViewsRealEstate> customerViewsRealEstateList = customer.getCustomerViewsRealEstates();
        
                if(customerViewsRealEstateList.size() > 0)
                {
                    for(CustomerViewsRealEstate customerViewsRealEstate2 : customerViewsRealEstateList)
                    {
                        if(customerViewsRealEstate.getCustomerViewsRealEstateId().equals(customerViewsRealEstate2.getCustomerViewsRealEstateId()))
                        {
                            customerViewsRealEstate2.setViewDate(LocalDateTime.now());
                            customerRepository.save(customer);
                            return realEstateCreationDTO;
                        }  
                    }
                }
                
                customer.addCustomerViewsRealEstate(customerViewsRealEstate);
                customerRepository.save(customer);
        
                return realEstateCreationDTO;
            }
        
        
        
            private RealEstateCreationDTO realEstateCreationDTOMapper(RealEstate realEstate) 
            {
                String title = realEstate.getTitle();
                String description = realEstate.getDescription();
                Double price = realEstate.getPrice();
                Double condoFee = realEstate.getCondoFee();
                String energyClass = realEstate.getEnergyClass().getValue();

                Double size = realEstate.getInternalFeatures().getSize();
                Integer roomsNumber = realEstate.getInternalFeatures().getRoomsNumber();
                String estateCondition = realEstate.getInternalFeatures().getEstateCondition().getValue();
                String furnitureCondition = realEstate.getInternalFeatures().getFurnitureCondition().getValue();

                Integer parkingSpacesNumber = realEstate.getExternalFeatures().getParkingSpacesNumber();
                Integer floorNumber = realEstate.getExternalFeatures().getFloorNumber();

                RealEstateMainFeatures realEstateMainFeatures = new RealEstateMainFeatures(title, description, price, condoFee, size, 
                                                                   roomsNumber, parkingSpacesNumber, floorNumber, 
                                                                   energyClass, estateCondition, furnitureCondition);


                RealEstateBooleanFeatures realEstateBooleanFeatures = new RealEstateBooleanFeatures();
                realEstateBooleanFeatures.setAirConditioning(realEstate.getInternalFeatures().getAirConditioning());
                realEstateBooleanFeatures.setHeating(realEstate.getInternalFeatures().getHeating());
                realEstateBooleanFeatures.setBalcony(realEstate.getExternalFeatures().getBalcony());
                realEstateBooleanFeatures.setElevator(realEstate.getExternalFeatures().getElevator());
                realEstateBooleanFeatures.setConcierge(realEstate.getExternalFeatures().getConcierge());
                realEstateBooleanFeatures.setGarage(realEstate.getExternalFeatures().getGarage());
                realEstateBooleanFeatures.setGarden(realEstate.getExternalFeatures().getGarden());
                realEstateBooleanFeatures.setSwimmingPool(realEstate.getExternalFeatures().getSwimmingPool());
                realEstateBooleanFeatures.setTerrace(realEstate.getExternalFeatures().getTerrace());


                RealEstateLocationFeatures realEstateLocationFeatures = new RealEstateLocationFeatures();
                realEstateLocationFeatures.setNearPark(realEstate.getExternalFeatures().getNearPark());
                realEstateLocationFeatures.setNearSchool(realEstate.getExternalFeatures().getNearSchool());
                realEstateLocationFeatures.setNearPublicTransport(realEstate.getExternalFeatures().getNearPublicTransport());


                AddressDTO addressDTO = modelMapper.map(realEstate.getAddress(), AddressDTO.class);


                if(realEstate instanceof RealEstateForSale)
                {
                    RealEstateForSale realEstateForSale = (RealEstateForSale) realEstate;
                    String notaryDeedState = realEstateForSale.getNotaryDeedState().getValue();
                    return new RealEstateForSaleCreationDTO(addressDTO, realEstateMainFeatures, realEstateBooleanFeatures, realEstateLocationFeatures, notaryDeedState);
                }
                else
                {
                    RealEstateForRent realEstateForSale = (RealEstateForRent) realEstate;
                    Double securityDeposit = realEstateForSale.getSecurityDeposit();
                    Integer contractYears = realEstateForSale.getContractYears();
                    return new RealEstateForRentCreationDTO(addressDTO, realEstateMainFeatures, realEstateBooleanFeatures, realEstateLocationFeatures, securityDeposit, contractYears);
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
