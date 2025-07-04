
package com.dietiEstates.backend.util;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.request.support.AddressDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.PropertyCondition;
import com.dietiEstates.backend.extra.ValidatableEnum;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;


@Component
@RequiredArgsConstructor
//@UtilityClass
public class RealEstateMappingUtil
{
    private final ModelMapper modelMapper;

    
    public RealEstateForRent fromDto(RealEstateForRentCreationDTO realEstateForRentCreationDTO)
    {
        RealEstate realEstate = fromDtoHelper(realEstateForRentCreationDTO);
        
        RealEstateForRent realEstateForRent = new RealEstateForRent(realEstate.getTitle(), realEstate.getDescription(), realEstate.getUploadingDate(), realEstate.getPrice(), realEstate.getCondoFee(), 
                                                                    realEstate.getEnergyClass(), realEstate.getInternalFeatures(), realEstate.getExternalFeatures(), 
                                                                    realEstateForRentCreationDTO.getSecurityDeposit(), realEstateForRentCreationDTO.getContractYears());
        
        Address address = modelMapper.map(realEstateForRentCreationDTO.getAddressDTO(), Address.class);
        realEstateForRent.addAddress(address);

        return realEstateForRent;
    }


    public RealEstateForSale fromDto(RealEstateForSaleCreationDTO realEstateForSaleCreationDTO)
    {
        RealEstate realEstate = fromDtoHelper(realEstateForSaleCreationDTO);
        
        RealEstateForSale realEstateForSale = new RealEstateForSale(realEstate.getTitle(), realEstate.getDescription(), realEstate.getUploadingDate(), realEstate.getPrice(), realEstate.getCondoFee(), 
                                                                    realEstate.getEnergyClass(), realEstate.getInternalFeatures(), realEstate.getExternalFeatures(), 
                                                                    ValidationUtil.enumValidator(NotaryDeedState.class, realEstateForSaleCreationDTO.getNotaryDeedState()));
        
        Address address = modelMapper.map(realEstateForSaleCreationDTO.getAddressDTO(), Address.class);
        realEstateForSale.addAddress(address);

        return realEstateForSale;
    }


            
            public RealEstateCreationDTO realEstateCreationDTOMapper(RealEstate realEstate) 
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



            private RealEstate fromDtoHelper(RealEstateCreationDTO realEstateCreationDTO)
            {
                String title = realEstateCreationDTO.getRealEstateMainFeaturesDTO().getTitle();
                String description = realEstateCreationDTO.getRealEstateMainFeaturesDTO().getDescription();
                Double price = realEstateCreationDTO.getRealEstateMainFeaturesDTO().getPrice();
                Double condoFee = realEstateCreationDTO.getRealEstateMainFeaturesDTO().getCondoFee();
                EnergyClass energyClass = ValidationUtil.enumValidator(EnergyClass.class, realEstateCreationDTO.getRealEstateMainFeaturesDTO().getEnergyClass());
                
                System.out.println("\n\nSONO IN MAPPING DA DTO A ENTITY:");
                //System.out.println("EnergyClass.valueOf(): " + EnergyClass.valueOf(realEstateCreationDTO.getRealEstateMainFeaturesDTO().getEnergyClass()));
                System.out.println("EnergyClass.of(): " + ValidatableEnum.of(EnergyClass.class, realEstateCreationDTO.getRealEstateMainFeaturesDTO().getEnergyClass()));


                InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateCreationDTO.getRealEstateMainFeaturesDTO().getSize(), 
                                                                       realEstateCreationDTO.getRealEstateMainFeaturesDTO().getRoomsNumber(), 
                                                                       ValidationUtil.enumValidator(PropertyCondition.class, realEstateCreationDTO.getRealEstateMainFeaturesDTO().getPropertyCondition()), 
                                                                       ValidationUtil.enumValidator(FurnitureCondition.class, realEstateCreationDTO.getRealEstateMainFeaturesDTO().getFurnitureCondition()),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isAirConditioning(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isBalcony());
                ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateCreationDTO.getRealEstateMainFeaturesDTO().getParkingSpacesNumber(), 
                                                                       realEstateCreationDTO.getRealEstateMainFeaturesDTO().getFloorNumber(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isElevator(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isConcierge(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isTerrace(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isGarage(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isBalcony(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isGarden(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().isSwimmingPool(),
                                                                       realEstateCreationDTO.getRealEstateLocationFeaturesDTO().isNearPark(),
                                                                       realEstateCreationDTO.getRealEstateLocationFeaturesDTO().isNearSchool(),
                                                                       realEstateCreationDTO.getRealEstateLocationFeaturesDTO().isNearPublicTransport());

                return new RealEstate(title, description, LocalDateTime.now(), price, condoFee, energyClass, internalRealEstateFeatures, externalRealEstateFeatures);
            }
}
