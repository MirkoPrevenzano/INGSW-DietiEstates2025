
package com.dietiEstates.backend.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.request.support.AddressDto;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;
import com.dietiEstates.backend.model.entity.Address;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.resolver.Supportable;

import org.modelmapper.ModelMapper;


public abstract class RealEstateCreationDtoMapper implements Supportable<RealEstate>
{  
    @Autowired
    private ModelMapper modelMapper;

    
    public final RealEstateCreationDto toDto(RealEstate entity)
    {
        RealEstateCreationDto dto = initializeDto();

        mapCommonFieldsToDto(entity, dto);
        mapSpecificFieldstoDto(entity, dto);

        return dto;
    }


    public final RealEstate toEntity(RealEstateCreationDto dto)
    {
        RealEstate entity = initializeEntity();

        mapCommonFieldsToEntity(dto, entity);
        mapSpecificFieldsToEntity(dto, entity);

        return entity;
    }


    public void mapCommonFieldsToEntity(RealEstateCreationDto dto, RealEstate entity) 
    {
        entity.setTitle(dto.getRealEstateMainFeaturesDto().getTitle());
        entity.setDescription(dto.getRealEstateMainFeaturesDto().getDescription());
        entity.setPrice(dto.getRealEstateMainFeaturesDto().getPrice());
        entity.setCondoFee(dto.getRealEstateMainFeaturesDto().getCondoFee());
        entity.setEnergyClass(dto.getRealEstateMainFeaturesDto().getEnergyClass());
        entity.setUploadingDate(LocalDateTime.now());

        InternalRealEstateFeatures internalRealEstateFeatures = new InternalRealEstateFeatures();
        internalRealEstateFeatures.setSize(dto.getRealEstateMainFeaturesDto().getSize());
        internalRealEstateFeatures.setRoomsNumber(dto.getRealEstateMainFeaturesDto().getRoomsNumber());
        internalRealEstateFeatures.setPropertyCondition(dto.getRealEstateMainFeaturesDto().getPropertyCondition());
        internalRealEstateFeatures.setFurnitureCondition(dto.getRealEstateMainFeaturesDto().getFurnitureCondition());
        internalRealEstateFeatures.setAirConditioning(dto.getRealEstateBooleanFeaturesDto().getAirConditioning());
        internalRealEstateFeatures.setHeating(dto.getRealEstateBooleanFeaturesDto().getHeating());

        ExternalRealEstateFeatures externalRealEstateFeatures = new ExternalRealEstateFeatures();
        externalRealEstateFeatures.setParkingSpacesNumber(dto.getRealEstateMainFeaturesDto().getParkingSpacesNumber());
        externalRealEstateFeatures.setFloorNumber(dto.getRealEstateMainFeaturesDto().getFloorNumber());
        externalRealEstateFeatures.setElevator(dto.getRealEstateBooleanFeaturesDto().getElevator());
        externalRealEstateFeatures.setConcierge(dto.getRealEstateBooleanFeaturesDto().getConcierge());
        externalRealEstateFeatures.setTerrace(dto.getRealEstateBooleanFeaturesDto().getTerrace());
        externalRealEstateFeatures.setBalcony(dto.getRealEstateBooleanFeaturesDto().getBalcony());
        externalRealEstateFeatures.setGarage(dto.getRealEstateBooleanFeaturesDto().getGarage());
        externalRealEstateFeatures.setGarden(dto.getRealEstateBooleanFeaturesDto().getGarden());
        externalRealEstateFeatures.setSwimmingPool(dto.getRealEstateBooleanFeaturesDto().getSwimmingPool());
        externalRealEstateFeatures.setNearSchool(dto.getRealEstateLocationFeaturesDto().getNearSchool());
        externalRealEstateFeatures.setNearPublicTransport(dto.getRealEstateLocationFeaturesDto().getNearPublicTransport());
        externalRealEstateFeatures.setNearPark(dto.getRealEstateLocationFeaturesDto().getNearPark());

        Address address = modelMapper.map(dto.getAddressDto(), Address.class);

        entity.setInternalFeatures(internalRealEstateFeatures);
        entity.setExternalFeatures(externalRealEstateFeatures);
        entity.setAddress(address);
    }
    

    public void mapCommonFieldsToDto(RealEstate entity, RealEstateCreationDto dto) 
    {
        RealEstateMainFeaturesDto realEstateMainFeaturesDto = new RealEstateMainFeaturesDto();
        realEstateMainFeaturesDto.setTitle(entity.getTitle());
        realEstateMainFeaturesDto.setDescription(entity.getDescription());
        realEstateMainFeaturesDto.setPrice(entity.getPrice());
        realEstateMainFeaturesDto.setCondoFee(entity.getCondoFee());
        realEstateMainFeaturesDto.setEnergyClass(entity.getEnergyClass());
        realEstateMainFeaturesDto.setSize(entity.getInternalFeatures().getSize());
        realEstateMainFeaturesDto.setRoomsNumber(entity.getInternalFeatures().getRoomsNumber());
        realEstateMainFeaturesDto.setPropertyCondition(entity.getInternalFeatures().getPropertyCondition());
        realEstateMainFeaturesDto.setFurnitureCondition(entity.getInternalFeatures().getFurnitureCondition());
        realEstateMainFeaturesDto.setParkingSpacesNumber(entity.getExternalFeatures().getParkingSpacesNumber());
        realEstateMainFeaturesDto.setFloorNumber(entity.getExternalFeatures().getFloorNumber());

        RealEstateBooleanFeaturesDto realEstateBooleanFeaturesDto = new RealEstateBooleanFeaturesDto();
        realEstateBooleanFeaturesDto.setAirConditioning(entity.getInternalFeatures().isAirConditioning());
        realEstateBooleanFeaturesDto.setHeating(entity.getInternalFeatures().isHeating());
        realEstateBooleanFeaturesDto.setBalcony(entity.getExternalFeatures().isBalcony());
        realEstateBooleanFeaturesDto.setElevator(entity.getExternalFeatures().isElevator());
        realEstateBooleanFeaturesDto.setConcierge(entity.getExternalFeatures().isConcierge());
        realEstateBooleanFeaturesDto.setGarage(entity.getExternalFeatures().isGarage());
        realEstateBooleanFeaturesDto.setGarden(entity.getExternalFeatures().isGarden());
        realEstateBooleanFeaturesDto.setSwimmingPool(entity.getExternalFeatures().isSwimmingPool());
        realEstateBooleanFeaturesDto.setTerrace(entity.getExternalFeatures().isTerrace());

        RealEstateLocationFeaturesDto realEstateLocationFeaturesDto = new RealEstateLocationFeaturesDto();
        realEstateLocationFeaturesDto.setNearPark(entity.getExternalFeatures().isNearPark());
        realEstateLocationFeaturesDto.setNearSchool(entity.getExternalFeatures().isNearSchool());
        realEstateLocationFeaturesDto.setNearPublicTransport(entity.getExternalFeatures().isNearPublicTransport());

        AddressDto addressDto = modelMapper.map(entity.getAddress(), AddressDto.class);

        dto.setRealEstateMainFeaturesDto(realEstateMainFeaturesDto);
        dto.setRealEstateBooleanFeaturesDto(realEstateBooleanFeaturesDto);
        dto.setRealEstateLocationFeaturesDto(realEstateLocationFeaturesDto);
        dto.setAddressDto(addressDto);
    }
    

    protected abstract RealEstateCreationDto initializeDto();
    protected abstract RealEstate initializeEntity();
    protected abstract void mapSpecificFieldstoDto(RealEstate entity, RealEstateCreationDto dto);
    protected abstract void mapSpecificFieldsToEntity(RealEstateCreationDto dto, RealEstate entity);  
}