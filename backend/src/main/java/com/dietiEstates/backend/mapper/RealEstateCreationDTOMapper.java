
package com.dietiEstates.backend.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.support.AddressDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDTO;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.resolver.Supportable;

import org.modelmapper.ModelMapper;


public abstract class RealEstateCreationDTOMapper implements Supportable<RealEstate>
{  
    @Autowired
    private ModelMapper modelMapper;

    
    public final RealEstateCreationDTO toDto(RealEstate entity)
    {
        RealEstateCreationDTO dto = initializeDto();

        mapCommonFieldsToDto(entity, dto);
        mapSpecificFieldstoDto(entity, dto);

        return dto;
    }


    public final RealEstate toEntity(RealEstateCreationDTO dto)
    {
        RealEstate entity = initializeEntity();

        mapCommonFieldsToEntity(dto, entity);
        mapSpecificFieldsToEntity(dto, entity);

        return entity;
    }


    public void mapCommonFieldsToEntity(RealEstateCreationDTO dto, RealEstate entity) 
    {
        entity.setTitle(dto.getRealEstateMainFeaturesDTO().getTitle());
        entity.setDescription(dto.getRealEstateMainFeaturesDTO().getDescription());
        entity.setPrice(dto.getRealEstateMainFeaturesDTO().getPrice());
        entity.setCondoFee(dto.getRealEstateMainFeaturesDTO().getCondoFee());
        entity.setEnergyClass(dto.getRealEstateMainFeaturesDTO().getEnergyClass());
        entity.setUploadingDate(LocalDateTime.now());

        InternalRealEstateFeatures internalRealEstateFeatures = new InternalRealEstateFeatures();
        internalRealEstateFeatures.setSize(dto.getRealEstateMainFeaturesDTO().getSize());
        internalRealEstateFeatures.setRoomsNumber(dto.getRealEstateMainFeaturesDTO().getRoomsNumber());
        internalRealEstateFeatures.setPropertyCondition(dto.getRealEstateMainFeaturesDTO().getPropertyCondition());
        internalRealEstateFeatures.setFurnitureCondition(dto.getRealEstateMainFeaturesDTO().getFurnitureCondition());
        internalRealEstateFeatures.setAirConditioning(dto.getRealEstateBooleanFeaturesDTO().getAirConditioning());
        internalRealEstateFeatures.setHeating(dto.getRealEstateBooleanFeaturesDTO().getHeating());

        ExternalRealEstateFeatures externalRealEstateFeatures = new ExternalRealEstateFeatures();
        externalRealEstateFeatures.setParkingSpacesNumber(dto.getRealEstateMainFeaturesDTO().getParkingSpacesNumber());
        externalRealEstateFeatures.setFloorNumber(dto.getRealEstateMainFeaturesDTO().getFloorNumber());
        externalRealEstateFeatures.setElevator(dto.getRealEstateBooleanFeaturesDTO().getElevator());
        externalRealEstateFeatures.setConcierge(dto.getRealEstateBooleanFeaturesDTO().getConcierge());
        externalRealEstateFeatures.setTerrace(dto.getRealEstateBooleanFeaturesDTO().getTerrace());
        externalRealEstateFeatures.setBalcony(dto.getRealEstateBooleanFeaturesDTO().getBalcony());
        externalRealEstateFeatures.setGarage(dto.getRealEstateBooleanFeaturesDTO().getGarage());
        externalRealEstateFeatures.setGarden(dto.getRealEstateBooleanFeaturesDTO().getGarden());
        externalRealEstateFeatures.setSwimmingPool(dto.getRealEstateBooleanFeaturesDTO().getSwimmingPool());
        externalRealEstateFeatures.setNearSchool(dto.getRealEstateLocationFeaturesDTO().getNearSchool());
        externalRealEstateFeatures.setNearPublicTransport(dto.getRealEstateLocationFeaturesDTO().getNearPublicTransport());
        externalRealEstateFeatures.setNearPark(dto.getRealEstateLocationFeaturesDTO().getNearPark());

        Address address = modelMapper.map(dto.getAddressDTO(), Address.class);

        entity.setInternalFeatures(internalRealEstateFeatures);
        entity.setExternalFeatures(externalRealEstateFeatures);
        entity.addAddress(address);
    }
    

    public void mapCommonFieldsToDto(RealEstate entity, RealEstateCreationDTO dto) 
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

        dto.setRealEstateMainFeaturesDTO(realEstateMainFeaturesDTO);
        dto.setRealEstateBooleanFeaturesDTO(realEstateBooleanFeaturesDTO);
        dto.setRealEstateLocationFeaturesDTO(realEstateLocationFeaturesDTO);
        dto.setAddressDTO(addressDTO);
    }
    

    protected abstract RealEstateCreationDTO initializeDto();
    protected abstract RealEstate initializeEntity();
    protected abstract void mapSpecificFieldstoDto(RealEstate entity, RealEstateCreationDTO dto);
    protected abstract void mapSpecificFieldsToEntity(RealEstateCreationDTO dto, RealEstate entity);  
}