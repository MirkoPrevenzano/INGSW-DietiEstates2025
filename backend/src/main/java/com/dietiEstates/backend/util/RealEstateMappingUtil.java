
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
import lombok.experimental.UtilityClass;


//@Component
//@RequiredArgsConstructor
@UtilityClass
public class RealEstateMappingUtil
{
   // private final ValidationUtil validationUtil;


    
    public RealEstateForRent realEstateForRentMapper(RealEstateForRentCreationDTO realEstateForRentCreationDTO)
    {
        String title = realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getTitle();
        String description = realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getDescription();
        Double price = realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getPrice();
        Double condoFee = realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getCondoFee();
        EnergyClass energyClass = ValidationUtil.enumValidator(EnergyClass.class, realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getEnergyClass()); 
        Double securityDeposit = realEstateForRentCreationDTO.getSecurityDeposit();
        Integer contractYears = realEstateForRentCreationDTO.getContractYears();

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getSize(), 
                                                                       realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getRoomsNumber(), 
                                                                       ValidationUtil.enumValidator(PropertyCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getPropertyCondition()), 
                                                                       ValidationUtil.enumValidator(FurnitureCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getFurnitureCondition()),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getAirConditioning(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getHeating());
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getParkingSpacesNumber(), 
                                                                       realEstateForRentCreationDTO.getRealEstateMainFeaturesDTO().getFloorNumber(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getElevator(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getConcierge(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getTerrace(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getGarage(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getBalcony(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getGarden(),
                                                                       realEstateForRentCreationDTO.getRealEstateBooleanFeaturesDTO().getSwimmingPool(),
                                                                       realEstateForRentCreationDTO.getRealEstateLocationFeaturesDTO().getNearPark(),
                                                                       realEstateForRentCreationDTO.getRealEstateLocationFeaturesDTO().getNearSchool(),
                                                                       realEstateForRentCreationDTO.getRealEstateLocationFeaturesDTO().getNearPublicTransport());
    
        return new RealEstateForRent(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, securityDeposit, contractYears);
    }


    public RealEstateForSale realEstateForSaleMapper(RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)
    {
        String title = realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getTitle();
        String description = realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getDescription();
        Double price = realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getPrice();
        Double condoFee = realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getCondoFee();
        EnergyClass energyClass = ValidationUtil.enumValidator(EnergyClass.class, realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getEnergyClass()); 
        NotaryDeedState notaryDeedState = ValidationUtil.enumValidator(NotaryDeedState.class, realEstateForSaleCreationDTO.getNotaryDeedState());

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getSize(), 
                                                                       realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getRoomsNumber(), 
                                                                       ValidationUtil.enumValidator(PropertyCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getPropertyCondition()), 
                                                                       ValidationUtil.enumValidator(FurnitureCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getFurnitureCondition()),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getAirConditioning(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getHeating());
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getParkingSpacesNumber(), 
                                                                       realEstateForSaleCreationDTO.getRealEstateMainFeaturesDTO().getFloorNumber(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getElevator(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getConcierge(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getTerrace(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getGarage(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getBalcony(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getGarden(),
                                                                       realEstateForSaleCreationDTO.getRealEstateBooleanFeaturesDTO().getSwimmingPool(),
                                                                       realEstateForSaleCreationDTO.getRealEstateLocationFeaturesDTO().getNearPark(),
                                                                       realEstateForSaleCreationDTO.getRealEstateLocationFeaturesDTO().getNearSchool(),
                                                                       realEstateForSaleCreationDTO.getRealEstateLocationFeaturesDTO().getNearPublicTransport());
                                                                       

        return new RealEstateForSale(title, description, LocalDateTime.now(), price, condoFee, energyClass, 
                                     internalRealEstateFeatures, externalRealEstateFeatures, notaryDeedState);
    }
    
}
