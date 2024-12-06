package com.example.datatier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.example.datatier.dto.AddressDTO;
import com.example.datatier.dto.PropertyRentDTO;
import com.example.datatier.service.administrator_service.AdministratorAsyncService;
import com.example.datatier.service.property_agent_service.PropertyAgentAsyncService;
import com.example.datatier.service.property_service.property_rent_service.PropertyRentService;



@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class DataTierServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataTierServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(PropertyRentService propertyRentService,
                                 AdministratorAsyncService administratorAsyncService,
                                 PropertyAgentAsyncService propertyAgentAsyncService) {
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
                            propertyRentService.saveNewPropertyRent(propertyRentDTO, "agentUsername", addressDTO)
                                .thenAccept(savedPropertyRentDTO -> {
                                    System.out.println("Property saved: " + savedPropertyRentDTO);
                                })
                                .exceptionally(ex -> {
									System.out.println(addressDTO);
                                    System.err.println("Error saving property: " + ex.getMessage());
                                    return null;
                                });
                        
                        
        };
    }
}