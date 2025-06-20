
package com.dietiEstates.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.dietiEstates.backend.dto.AddressDTO;
import com.dietiEstates.backend.dto.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.RealEstateMainFeaturesDTO;
import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.util.JFreeChartUtil;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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



    public RealEstateCreationDTO viewRealEstate(String username, Long realEstateId, Authentication authentication) 
    {
        log.info("\n\nENTRO NEL METODO VIEW REAL ESTATE:\n");

        log.info("\n\nFIND BY USERNAME (CustomerRepo):\n");
        Customer customer = customerRepository.findByUsername(username).get();

        log.info("\n\nFIND BY ID (RealEstateRepo):\n");
        RealEstate realEstate = realEstateRepository.findById(realEstateId).get();

        log.info("\n\nGET AGENT FROM REAL ESTATE:\n");
        Agent agent = realEstate.getAgent();

/*         
find user;
find real estate;

realestate.getAgent 
modelmapper.map(agentinfo,agent)
map from realestate to realestate creation
create realestatecompleteDTO

if(context == customer)
        updating realestate viewsnumber
        creazione custviewrealest
        save custview

return realestatecompleteDTO;

 */

         // 2. Verifica se l'utente autenticato ha il ruolo CUSTOMER
        if(authentication != null && authentication.isAuthenticated()) 
        {
            if(authentication.getPrincipal() instanceof Customer)
                log.info("\n\nil mio principal è un customerrrr\n\n");

                if(authentication.getPrincipal() instanceof Agent)
                log.info("\n\nil mio principal è un agentttt\n\n");


            UserDetails user = (UserDetails) authentication.getPrincipal();

            log.info("\n\n\nSONO NELL'IF DEL CUSTOMERR");
        }
           
        RealEstateCreationDTO realEstateCreationDTO = realEstateCreationDTOMapper(realEstate);
                

        
                long newViewsNumber = realEstate.getRealEstateStats().getViewsNumber() + 1;
                realEstate.getRealEstateStats().setViewsNumber(newViewsNumber);
        
                CustomerViewsRealEstate customerViewsRealEstate = new CustomerViewsRealEstate(
                                                                        new CustomerViewsRealEstateId(customer.getUserId(), realEstate.getRealEstateId()), 
                                                                        LocalDateTime.now(), 
                                                                        customer,
                                                                        realEstate);
        
                log.info("\n\nGET CUSTOMERVIEWSREALESTATE:\n");
/*                 List<CustomerViewsRealEstate> customerViewsRealEstateList = customer.getCustomerViewsRealEstates();
        
                if(customerViewsRealEstateList.size() > 0)
                {
                    for(CustomerViewsRealEstate customerViewsRealEstate2 : customerViewsRealEstateList)
                    {
                        if(customerViewsRealEstate.getCustomerViewsRealEstateId().equals(customerViewsRealEstate2.getCustomerViewsRealEstateId()))
                        {
                            log.info("\n\nMERGE (CustomerRepo):\n");
                            customerViewsRealEstate2.setViewDate(LocalDateTime.now());
                            customerRepository.save(customer);
                            return realEstateCreationDTO;
                        }  
                    }
                } */
                
                customer.addCustomerViewsRealEstate(customerViewsRealEstate);
                log.info("\n\nSAVE (CustomerRepo):\n");
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
                String propertyCondition = realEstate.getInternalFeatures().getPropertyCondition().getValue();
                String furnitureCondition = realEstate.getInternalFeatures().getFurnitureCondition().getValue();

                Integer parkingSpacesNumber = realEstate.getExternalFeatures().getParkingSpacesNumber();
                Integer floorNumber = realEstate.getExternalFeatures().getFloorNumber();

                RealEstateMainFeaturesDTO realEstateMainFeaturesDTO = new RealEstateMainFeaturesDTO(title, description, price, condoFee, size, 
                                                                   roomsNumber, parkingSpacesNumber, floorNumber, 
                                                                   energyClass, propertyCondition, furnitureCondition);


                RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO = new RealEstateBooleanFeaturesDTO();
                realEstateBooleanFeaturesDTO.setAirConditioning(realEstate.getInternalFeatures().getAirConditioning());
                realEstateBooleanFeaturesDTO.setHeating(realEstate.getInternalFeatures().getHeating());
                realEstateBooleanFeaturesDTO.setBalcony(realEstate.getExternalFeatures().getBalcony());
                realEstateBooleanFeaturesDTO.setElevator(realEstate.getExternalFeatures().getElevator());
                realEstateBooleanFeaturesDTO.setConcierge(realEstate.getExternalFeatures().getConcierge());
                realEstateBooleanFeaturesDTO.setGarage(realEstate.getExternalFeatures().getGarage());
                realEstateBooleanFeaturesDTO.setGarden(realEstate.getExternalFeatures().getGarden());
                realEstateBooleanFeaturesDTO.setSwimmingPool(realEstate.getExternalFeatures().getSwimmingPool());
                realEstateBooleanFeaturesDTO.setTerrace(realEstate.getExternalFeatures().getTerrace());


                RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO = new RealEstateLocationFeaturesDTO();
                realEstateLocationFeaturesDTO.setNearPark(realEstate.getExternalFeatures().getNearPark());
                realEstateLocationFeaturesDTO.setNearSchool(realEstate.getExternalFeatures().getNearSchool());
                realEstateLocationFeaturesDTO.setNearPublicTransport(realEstate.getExternalFeatures().getNearPublicTransport());


                AddressDTO addressDTO = modelMapper.map(realEstate.getAddress(), AddressDTO.class);


                if(realEstate instanceof RealEstateForSale)
                {
                    RealEstateForSale realEstateForSale = (RealEstateForSale) realEstate;
                    String notaryDeedState = realEstateForSale.getNotaryDeedState().getValue();
                    return new RealEstateForSaleCreationDTO(addressDTO, realEstateMainFeaturesDTO, realEstateBooleanFeaturesDTO, realEstateLocationFeaturesDTO, notaryDeedState);
                }
                else
                {
                    RealEstateForRent realEstateForSale = (RealEstateForRent) realEstate;
                    Double securityDeposit = realEstateForSale.getSecurityDeposit();
                    Integer contractYears = realEstateForSale.getContractYears();
                    return new RealEstateForRentCreationDTO(addressDTO, realEstateMainFeaturesDTO, realEstateBooleanFeaturesDTO, realEstateLocationFeaturesDTO, securityDeposit, contractYears);
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
