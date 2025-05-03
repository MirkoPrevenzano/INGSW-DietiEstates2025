
package com.dietiEstates.backend.factory;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.dto.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.RealEstatePreviewDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.EstateCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.model.RealEstate;
import com.dietiEstates.backend.model.RealEstateForRent;
import com.dietiEstates.backend.model.RealEstateForSale;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.service.RealEstateMapper;
import com.dietiEstates.backend.service.ValidatorService;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public class RealEstateFactory 
{
    private final RealEstateMapper realEstateMapper;



   public RealEstate createRealEstateFromDTO(RealEstateCreationDTO realEstateCreationDTO) 
   {
        RealEstate realEstate = null;

        if(realEstateCreationDTO instanceof RealEstateForRentCreationDTO)
            realEstate = realEstateMapper.realEstateForRentMapper((RealEstateForRentCreationDTO) realEstateCreationDTO);
        else if(realEstateCreationDTO instanceof RealEstateForSaleCreationDTO)
            realEstate = realEstateMapper.realEstateForSaleMapper((RealEstateForSaleCreationDTO) realEstateCreationDTO);
        else
            ;
        
        return realEstate;
   };


   public static Root<?> createRealEstateRootFromType(String realEstateType, CriteriaQuery<RealEstatePreviewDTO> criteriaQuery)
   {
        Root<?> realEstate = null;

        if(realEstateType.equals("For Sale"))
            realEstate = criteriaQuery.from(RealEstateForSale.class);
        else if(realEstateType.equals("For Rent"))
            realEstate = criteriaQuery.from(RealEstateForRent.class);
        else
            ; // throw Exception

        return realEstate;
   }



/*        private RealEstateForRent realEstateForRentMapper(RealEstateForRentCreationDTO realEstateForRentCreationDTO)
    {
        String title = realEstateForRentCreationDTO.getRealEstateMainFeatures().getTitle();
        String description = realEstateForRentCreationDTO.getRealEstateMainFeatures().getDescription();
        Double price = realEstateForRentCreationDTO.getRealEstateMainFeatures().getPrice();
        Double condoFee = realEstateForRentCreationDTO.getRealEstateMainFeatures().getCondoFee();
        EnergyClass energyClass = validatorService.enumValidator(EnergyClass.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getEnergyClass()); 
        Double securityDeposit = realEstateForRentCreationDTO.getSecurityDeposit();
        Integer contractYears = realEstateForRentCreationDTO.getContractYears();

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForRentCreationDTO.getRealEstateMainFeatures().getSize(), 
                                                                       realEstateForRentCreationDTO.getRealEstateMainFeatures().getRoomsNumber(), 
                                                                       validatorService.enumValidator(EstateCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getEstateCondition()), 
                                                                       validatorService.enumValidator(FurnitureCondition.class, realEstateForRentCreationDTO.getRealEstateMainFeatures().getFurnitureCondition()));
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


        private RealEstateForSale realEstateForSaleMapper(RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)
    {
        String title = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getTitle();
        String description = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getDescription();
        Double price = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getPrice();
        Double condoFee = realEstateForSaleCreationDTO.getRealEstateMainFeatures().getCondoFee();
        EnergyClass energyClass = validatorService.enumValidator(EnergyClass.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getEnergyClass()); 
        NotaryDeedState notaryDeedState = validatorService.enumValidator(NotaryDeedState.class, realEstateForSaleCreationDTO.getNotaryDeedState());

        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateForSaleCreationDTO.getRealEstateMainFeatures().getSize(), 
                                                                       realEstateForSaleCreationDTO.getRealEstateMainFeatures().getRoomsNumber(), 
                                                                       validatorService.enumValidator(EstateCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getEstateCondition()), 
                                                                       validatorService.enumValidator(FurnitureCondition.class, realEstateForSaleCreationDTO.getRealEstateMainFeatures().getFurnitureCondition()));
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
    } */
}