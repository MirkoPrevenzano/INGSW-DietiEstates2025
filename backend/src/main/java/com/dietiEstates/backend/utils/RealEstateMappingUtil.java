
package com.dietiEstates.backend.utils;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.EstateCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.model.RealEstateForRent;
import com.dietiEstates.backend.model.RealEstateForSale;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateMappingUtil
{
    private final ValidationUtil validationUtil;


    
    public RealEstateForRent realEstateForRentMapper(RealEstateForRentCreationDTO realEstateForRentCreationDTO)
    {
        String title = realEstateForRentCreationDTO.getRealEstateMainFeatures().getTitle();
        String description = realEstateForRentCreationDTO.getRealEstateMainFeatures().getDescription();
        Double price = realEstateForRentCreationDTO.getRealEstateMainFeatures().getPrice();
        Double condoFee = realEstateForRentCreationDTO.getRealEstateMainFeatures().getCondoFee();
        EnergyClass energyClass = validationUtil.enumValidator(EnergyClass.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getEnergyClass()); 
        Double securityDeposit = realEstateForRentCreationDTO.getSecurityDeposit();
        Integer contractYears = realEstateForRentCreationDTO.getContractYears();

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForRentCreationDTO.getRealEstateMainFeatures().getSize(), 
                                                                       realEstateForRentCreationDTO.getRealEstateMainFeatures().getRoomsNumber(), 
                                                                       validationUtil.enumValidator(EstateCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getEstateCondition()), 
                                                                       validationUtil.enumValidator(FurnitureCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getFurnitureCondition()));
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForRentCreationDTO.getRealEstateMainFeatures().getParkingSpacesNumber(), 
                                                                       realEstateForRentCreationDTO.getRealEstateMainFeatures().getFloorNumber());
        
        internalRealEstateFeatures.setAirConditioning(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getAirConditioning());
        internalRealEstateFeatures.setHeating(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getHeating());

        externalRealEstateFeatures.setElevator(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getElevator());
        externalRealEstateFeatures.setConcierge(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getConcierge());
        externalRealEstateFeatures.setTerrace(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getTerrace());
        externalRealEstateFeatures.setGarage(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getGarage());
        externalRealEstateFeatures.setBalcony(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getBalcony());
        externalRealEstateFeatures.setGarden(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getGarden());
        externalRealEstateFeatures.setSwimmingPool(realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getSwimmingPool());
        externalRealEstateFeatures.setNearPark(realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearPark());
        externalRealEstateFeatures.setNearSchool(realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearSchool());
        externalRealEstateFeatures.setNearPublicTransport(realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearPublicTransport());
    
        return new RealEstateForRent(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, securityDeposit, contractYears);
    }


    public RealEstateForSale realEstateForSaleMapper(RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)
    {
        String title = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getTitle();
        String description = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getDescription();
        Double price = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getPrice();
        Double condoFee = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getCondoFee();
        EnergyClass energyClass = validationUtil.enumValidator(EnergyClass.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getEnergyClass()); 
        NotaryDeedState notaryDeedState = validationUtil.enumValidator(NotaryDeedState.class, realEstateForSaleCreationDTO.getNotaryDeedState());

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForSaleCreationDTO.getRealEstateMainFeatures().getSize(), 
                                                                       realEstateForSaleCreationDTO.getRealEstateMainFeatures().getRoomsNumber(), 
                                                                       validationUtil.enumValidator(EstateCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getEstateCondition()), 
                                                                       validationUtil.enumValidator(FurnitureCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getFurnitureCondition()));
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForSaleCreationDTO.getRealEstateMainFeatures().getParkingSpacesNumber(), 
                                                                       realEstateForSaleCreationDTO.getRealEstateMainFeatures().getFloorNumber());
        
        internalRealEstateFeatures.setAirConditioning(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getAirConditioning());
        internalRealEstateFeatures.setHeating(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getHeating());

        externalRealEstateFeatures.setElevator(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getElevator());
        externalRealEstateFeatures.setConcierge(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getConcierge());
        externalRealEstateFeatures.setTerrace(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getTerrace());
        externalRealEstateFeatures.setGarage(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getGarage());
        externalRealEstateFeatures.setBalcony(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getBalcony());
        externalRealEstateFeatures.setGarden(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getGarden());
        externalRealEstateFeatures.setSwimmingPool(realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getSwimmingPool());
        externalRealEstateFeatures.setNearPark(realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearPark());
        externalRealEstateFeatures.setNearSchool(realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearSchool());
        externalRealEstateFeatures.setNearPublicTransport(realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearPublicTransport());

        return new RealEstateForSale(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, notaryDeedState);
    }
    
}
