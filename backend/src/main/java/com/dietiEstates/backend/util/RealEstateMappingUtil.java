
package com.dietiEstates.backend.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.PropertyCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;

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
                                                                       validationUtil.enumValidator(PropertyCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getPropertyCondition()), 
                                                                       validationUtil.enumValidator(FurnitureCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getFurnitureCondition()),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getAirConditioning(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getHeating());
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForRentCreationDTO.getRealEstateMainFeatures().getParkingSpacesNumber(), 
                                                                       realEstateForRentCreationDTO.getRealEstateMainFeatures().getFloorNumber(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getElevator(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getConcierge(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getTerrace(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getGarage(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getBalcony(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getGarden(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeatures().getSwimmingPool(),
                                                                       realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearPark(),
                                                                       realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearSchool(),
                                                                       realEstateForRentCreationDTO.getRealEstateLocationFeatures().getNearPublicTransport());
    
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
                                                                       validationUtil.enumValidator(PropertyCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getPropertyCondition()), 
                                                                       validationUtil.enumValidator(FurnitureCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getFurnitureCondition()),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getAirConditioning(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getHeating());
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForSaleCreationDTO.getRealEstateMainFeatures().getParkingSpacesNumber(), 
                                                                       realEstateForSaleCreationDTO.getRealEstateMainFeatures().getFloorNumber(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getElevator(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getConcierge(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getTerrace(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getGarage(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getBalcony(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getGarden(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeatures().getSwimmingPool(),
                                                                       realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearPark(),
                                                                       realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearSchool(),
                                                                       realEstateForSaleCreationDTO.getRealEstateLocationFeatures().getNearPublicTransport());
                                                                       

        return new RealEstateForSale(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, notaryDeedState);
    }
    
}
