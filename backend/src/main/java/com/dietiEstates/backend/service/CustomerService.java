
package com.dietiEstates.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.dietiEstates.backend.controller.CSVController;
import com.dietiEstates.backend.dto.AddressDTO;
import com.dietiEstates.backend.dto.EstateDescribe;
import com.dietiEstates.backend.dto.EstateFeatures;
import com.dietiEstates.backend.dto.EstateLocationFeatures;
import com.dietiEstates.backend.dto.RealEstateCompleteDTO;
import com.dietiEstates.backend.dto.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.model.Address;
import com.dietiEstates.backend.model.Customer;
import com.dietiEstates.backend.model.CustomerViewsRealEstate;
import com.dietiEstates.backend.model.RealEstate;
import com.dietiEstates.backend.model.RealEstateForRent;
import com.dietiEstates.backend.model.RealEstateForSale;
import com.dietiEstates.backend.model.embeddable.CustomerViewsRealEstateId;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService 
{

    private final JFreeChartService JFreeChartService;

    private final CSVController CSVController;
    private final RealEstateRepository realEstateRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;



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
                String energyClass = realEstate.getEnergyClass();

                Double size = realEstate.getInternalFeatures().getSize();
                Integer roomsNumber = realEstate.getInternalFeatures().getRoomsNumber();
                String estateCondition = realEstate.getInternalFeatures().getEstateCondition();
                String furnitureCondition = realEstate.getInternalFeatures().getFurnitureCondition();

                Integer parkingSpacesNumber = realEstate.getExternalFeatures().getParkingSpacesNumber();
                Integer floorNumber = realEstate.getExternalFeatures().getFloorNumber();

                EstateDescribe estateDescribe = new EstateDescribe(description, title, price, condoFee, energyClass, size, 
                                                                   roomsNumber, estateCondition, furnitureCondition, parkingSpacesNumber, floorNumber);


                EstateFeatures estateFeatures = new EstateFeatures();
                estateFeatures.setHasAirConditioning(realEstate.getInternalFeatures().isAirConditioning());
                estateFeatures.setHasHeating(realEstate.getInternalFeatures().isHeating());
                estateFeatures.setHasBalcony(realEstate.getExternalFeatures().isBalcony());
                estateFeatures.setHasElevator(realEstate.getExternalFeatures().isElevator());
                estateFeatures.setHasConcierge(realEstate.getExternalFeatures().isConcierge());
                estateFeatures.setHasGarage(realEstate.getExternalFeatures().isGarage());
                estateFeatures.setHasGarden(realEstate.getExternalFeatures().isGarden());
                estateFeatures.setHasSwimmingPool(realEstate.getExternalFeatures().isSwimmingPool());
                estateFeatures.setHasTerrace(realEstate.getExternalFeatures().isTerrace());


                EstateLocationFeatures estateLocationFeatures = new EstateLocationFeatures();
                estateLocationFeatures.setNearPark(realEstate.getExternalFeatures().isNearPark());
                estateLocationFeatures.setNearSchool(realEstate.getExternalFeatures().isNearSchool());
                estateLocationFeatures.setNearPublicTransport(realEstate.getExternalFeatures().isNearPublicTransport());


                AddressDTO address = modelMapper.map(realEstate.getAddress(), AddressDTO.class);


                if(realEstate instanceof RealEstateForSale)
                {
                    RealEstateForSale realEstateForSale = (RealEstateForSale) realEstate;
                    String notaryDeedState = realEstateForSale.getNotaryDeedState();
                    return new RealEstateForSaleCreationDTO(address, estateDescribe, estateFeatures, estateLocationFeatures, notaryDeedState);
                }
                else
                {
                    RealEstateForRent realEstateForSale = (RealEstateForRent) realEstate;
                    Double securityDeposit = realEstateForSale.getSecurityDeposit();
                    Integer contractYears = realEstateForSale.getContractYears();
                    return new RealEstateForRentCreationDTO(address, estateDescribe, estateFeatures, estateLocationFeatures, securityDeposit, contractYears);
                }
            } 
}
