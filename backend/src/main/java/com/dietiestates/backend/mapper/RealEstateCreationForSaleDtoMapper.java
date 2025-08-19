
package com.dietiestates.backend.mapper;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.dto.request.RealEstateForSaleCreationDto;
import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.model.entity.RealEstateForSale;

import org.modelmapper.ModelMapper;


@Component
public class RealEstateCreationForSaleDtoMapper extends RealEstateCreationDtoMapper
{
    public RealEstateCreationForSaleDtoMapper(ModelMapper modelMapper) 
    {
        super(modelMapper);
    }


    @Override
    protected RealEstateForSaleCreationDto initializeDto() 
    {
        return new RealEstateForSaleCreationDto();
    }

    @Override
    protected RealEstateForSale initializeEntity() 
    {
        return new RealEstateForSale();
    }

    @Override
    protected void mapSpecificFieldsToEntity(RealEstateCreationDto dto, RealEstate entity) 
    {
        ((RealEstateForSale) entity).setNotaryDeedState(((RealEstateForSaleCreationDto) dto).getNotaryDeedState());
    }

    @Override
    protected void mapSpecificFieldstoDto(RealEstate entity, RealEstateCreationDto dto) 
    {
        ((RealEstateForSaleCreationDto) dto).setNotaryDeedState(((RealEstateForSale) entity).getNotaryDeedState());

        dto.setContractType(ContractType.FOR_SALE);
    }


    @Override
    public boolean supports(RealEstate realEstate) 
    {
        return RealEstateForSale.class.isAssignableFrom(realEstate.getClass());
    }
}