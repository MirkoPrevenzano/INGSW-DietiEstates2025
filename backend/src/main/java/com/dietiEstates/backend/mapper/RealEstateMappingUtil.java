
package com.dietiEstates.backend.mapper;

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
import com.dietiEstates.backend.util.ValidationUtil;

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
                                                                    NotaryDeedState.BARE_PROPERTY);
        
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
                EnergyClass energyClass = realEstate.getEnergyClass();

                Double size = realEstate.getInternalFeatures().getSize();
                Integer roomsNumber = realEstate.getInternalFeatures().getRoomsNumber();
                PropertyCondition propertyCondition = realEstate.getInternalFeatures().getPropertyCondition();
                FurnitureCondition furnitureCondition = realEstate.getInternalFeatures().getFurnitureCondition();

                Integer parkingSpacesNumber = realEstate.getExternalFeatures().getParkingSpacesNumber();
                Integer floorNumber = realEstate.getExternalFeatures().getFloorNumber();

                RealEstateMainFeaturesDTO realEstateMainFeaturesDTO = new RealEstateMainFeaturesDTO(title, description, price, condoFee, size, 
                                                                   roomsNumber, parkingSpacesNumber, floorNumber, 
                                                                   energyClass, propertyCondition, furnitureCondition);


                RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO = new RealEstateBooleanFeaturesDTO();
                realEstateBooleanFeaturesDTO.setAirConditioning(realEstate.getInternalFeatures().isAirConditioning());
                realEstateBooleanFeaturesDTO.setHeating(realEstate.getInternalFeatures().isHeating());
                realEstateBooleanFeaturesDTO.setBalcony(realEstate.getExternalFeatures().isBalcony());
                realEstateBooleanFeaturesDTO.setElevator(realEstate.getExternalFeatures().isElevator());
                realEstateBooleanFeaturesDTO.setConcierge(realEstate.getExternalFeatures().isConcierge());
                realEstateBooleanFeaturesDTO.setGarage(realEstate.getExternalFeatures().isGarage());
                realEstateBooleanFeaturesDTO.setGarden(realEstate.getExternalFeatures().isGarden());
                realEstateBooleanFeaturesDTO.setSwimmingPool(realEstate.getExternalFeatures().isSwimmingPool());
                realEstateBooleanFeaturesDTO.setTerrace(realEstate.getExternalFeatures().isTerrace());


                RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO = new RealEstateLocationFeaturesDTO();
                realEstateLocationFeaturesDTO.setNearPark(realEstate.getExternalFeatures().isNearPark());
                realEstateLocationFeaturesDTO.setNearSchool(realEstate.getExternalFeatures().isNearSchool());
                realEstateLocationFeaturesDTO.setNearPublicTransport(realEstate.getExternalFeatures().isNearPublicTransport());


                AddressDTO addressDTO = modelMapper.map(realEstate.getAddress(), AddressDTO.class);


                if(realEstate instanceof RealEstateForSale)
                {
                    RealEstateForSale realEstateForSale = (RealEstateForSale) realEstate;
                    NotaryDeedState notaryDeedState = realEstateForSale.getNotaryDeedState();
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
                EnergyClass energyClass = realEstateCreationDTO.getRealEstateMainFeaturesDTO().getEnergyClass();
                
                System.out.println("\n\nSONO IN MAPPING DA DTO A ENTITY:");
                //System.out.println("EnergyClass.valueOf(): " + EnergyClass.valueOf(realEstateCreationDTO.getRealEstateMainFeaturesDTO().getEnergyClass()));
                //System.out.println("EnergyClass.of(): " + ValidatableEnum.of(EnergyClass.class, realEstateCreationDTO.getRealEstateMainFeaturesDTO().getEnergyClass()));

                // TODO: controllare i NULL
                InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(realEstateCreationDTO.getRealEstateMainFeaturesDTO().getSize(), 
                                                                       realEstateCreationDTO.getRealEstateMainFeaturesDTO().getRoomsNumber(), 
                                                                       realEstateCreationDTO.getRealEstateMainFeaturesDTO().getPropertyCondition(), 
                                                                       realEstateCreationDTO.getRealEstateMainFeaturesDTO().getFurnitureCondition(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getAirConditioning(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getBalcony());
                ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(realEstateCreationDTO.getRealEstateMainFeaturesDTO().getParkingSpacesNumber(), 
                                                                       realEstateCreationDTO.getRealEstateMainFeaturesDTO().getFloorNumber(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getElevator(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getConcierge(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getTerrace(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getGarage(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getBalcony(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getGarden(),
                                                                       realEstateCreationDTO.getRealEstateBooleanFeaturesDTO().getSwimmingPool(),
                                                                       realEstateCreationDTO.getRealEstateLocationFeaturesDTO().getNearPark(),
                                                                       realEstateCreationDTO.getRealEstateLocationFeaturesDTO().getNearSchool(),
                                                                       realEstateCreationDTO.getRealEstateLocationFeaturesDTO().getNearPublicTransport());

                return new RealEstate(title, description, LocalDateTime.now(), price, condoFee, energyClass, internalRealEstateFeatures, externalRealEstateFeatures);
            }
}
