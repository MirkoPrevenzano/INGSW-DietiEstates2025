
package com.dietiEstates.backend.mapper;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.support.AddressDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDTO;
import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.RealEstate;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class RealEstateCreationDTOMapperr<D extends RealEstateCreationDTO, E extends RealEstate> 
{  
    @Autowired
    private ModelMapper modelMapper;



    protected void mapCommonFieldsToEntity(D dto, E entity) 
    {
        entity.setTitle(dto.getRealEstateMainFeaturesDTO().getTitle());
        entity.setDescription(dto.getRealEstateMainFeaturesDTO().getDescription());
        entity.setPrice(dto.getRealEstateMainFeaturesDTO().getPrice());
        entity.setPrice(dto.getRealEstateMainFeaturesDTO().getPrice());
        entity.setCondoFee(dto.getRealEstateMainFeaturesDTO().getCondoFee());
        entity.setEnergyClass(dto.getRealEstateMainFeaturesDTO().getEnergyClass());
        entity.setUploadingDate(LocalDateTime.now());

        // TODO: controllare i NULL
        InternalRealEstateFeatures internalRealEstateFeatures = 
                                        new InternalRealEstateFeatures(dto.getRealEstateMainFeaturesDTO().getSize(), 
                                                                       dto.getRealEstateMainFeaturesDTO().getRoomsNumber(), 
                                                                       dto.getRealEstateMainFeaturesDTO().getPropertyCondition(), 
                                                                       dto.getRealEstateMainFeaturesDTO().getFurnitureCondition(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getAirConditioning(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getBalcony());
        ExternalRealEstateFeatures externalRealEstateFeatures = 
                                        new ExternalRealEstateFeatures(dto.getRealEstateMainFeaturesDTO().getParkingSpacesNumber(), 
                                                                       dto.getRealEstateMainFeaturesDTO().getFloorNumber(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getElevator(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getConcierge(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getTerrace(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getGarage(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getBalcony(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getGarden(),
                                                                       dto.getRealEstateBooleanFeaturesDTO().getSwimmingPool(),
                                                                       dto.getRealEstateLocationFeaturesDTO().getNearPark(),
                                                                       dto.getRealEstateLocationFeaturesDTO().getNearSchool(),
                                                                       dto.getRealEstateLocationFeaturesDTO().getNearPublicTransport());
        
        entity.setInternalFeatures(internalRealEstateFeatures);
        entity.setExternalFeatures(externalRealEstateFeatures);

        Address address = modelMapper.map(dto.getAddressDTO(), Address.class);
        entity.addAddress(address);

    }
    

    protected void mapCommonFieldsToDto(E entity, D dto) 
    {
        RealEstateMainFeaturesDTO realEstateMainFeaturesDTO = new RealEstateMainFeaturesDTO();
        realEstateMainFeaturesDTO.setTitle(entity.getTitle());
        realEstateMainFeaturesDTO.setDescription(entity.getDescription());
        realEstateMainFeaturesDTO.setPrice(entity.getPrice());
        realEstateMainFeaturesDTO.setCondoFee(entity.getCondoFee());
        realEstateMainFeaturesDTO.setEnergyClass(entity.getEnergyClass());
        realEstateMainFeaturesDTO.setSize(entity.getInternalFeatures().getSize());
        realEstateMainFeaturesDTO.setRoomsNumber(entity.getInternalFeatures().getRoomsNumber());
        realEstateMainFeaturesDTO.setPropertyCondition(entity.getInternalFeatures().getPropertyCondition());
        realEstateMainFeaturesDTO.setFurnitureCondition(entity.getInternalFeatures().getFurnitureCondition());
        realEstateMainFeaturesDTO.setParkingSpacesNumber(entity.getExternalFeatures().getParkingSpacesNumber());
        realEstateMainFeaturesDTO.setFloorNumber(entity.getExternalFeatures().getFloorNumber());

        RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO = new RealEstateBooleanFeaturesDTO();
        realEstateBooleanFeaturesDTO.setAirConditioning(entity.getInternalFeatures().isAirConditioning());
        realEstateBooleanFeaturesDTO.setHeating(entity.getInternalFeatures().isHeating());
        realEstateBooleanFeaturesDTO.setBalcony(entity.getExternalFeatures().isBalcony());
        realEstateBooleanFeaturesDTO.setElevator(entity.getExternalFeatures().isElevator());
        realEstateBooleanFeaturesDTO.setConcierge(entity.getExternalFeatures().isConcierge());
        realEstateBooleanFeaturesDTO.setGarage(entity.getExternalFeatures().isGarage());
        realEstateBooleanFeaturesDTO.setGarden(entity.getExternalFeatures().isGarden());
        realEstateBooleanFeaturesDTO.setSwimmingPool(entity.getExternalFeatures().isSwimmingPool());
        realEstateBooleanFeaturesDTO.setTerrace(entity.getExternalFeatures().isTerrace());


        RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO = new RealEstateLocationFeaturesDTO();
        realEstateLocationFeaturesDTO.setNearPark(entity.getExternalFeatures().isNearPark());
        realEstateLocationFeaturesDTO.setNearSchool(entity.getExternalFeatures().isNearSchool());
        realEstateLocationFeaturesDTO.setNearPublicTransport(entity.getExternalFeatures().isNearPublicTransport());

        AddressDTO addressDTO = modelMapper.map(entity.getAddress(), AddressDTO.class);
        dto.setAddressDTO(addressDTO);
    }


    public abstract E toEntity(D dto);


    public abstract D toDto(E entity);
}
