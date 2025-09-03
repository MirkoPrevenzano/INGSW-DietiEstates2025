
package com.dietiestates.backend.mapper;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.dto.request.RealEstateForRentCreationDto;
import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.model.entity.RealEstateForRent;

import org.modelmapper.ModelMapper;


@Component
public class RealEstateCreationForRentDtoMapper extends RealEstateCreationDtoMapper
{
    public RealEstateCreationForRentDtoMapper(ModelMapper modelMapper) 
    {
        super(modelMapper);
    }



    @Override
    protected RealEstateForRentCreationDto initializeDto() 
    {
        return new RealEstateForRentCreationDto();
    }


    @Override
    protected RealEstateForRent initializeEntity() 
    {
        return new RealEstateForRent();
    }


    @Override
    protected void mapSpecificFieldsToEntity(RealEstateCreationDto dto, RealEstate entity) 
    {
        ((RealEstateForRent) entity).setSecurityDeposit(((RealEstateForRentCreationDto) dto).getSecurityDeposit());
        ((RealEstateForRent) entity).setContractYears(((RealEstateForRentCreationDto) dto).getContractYears());
    }


    @Override
    protected void mapSpecificFieldstoDto(RealEstate entity, RealEstateCreationDto dto) 
    {
        ((RealEstateForRentCreationDto) dto).setSecurityDeposit(((RealEstateForRent) entity).getSecurityDeposit());
        ((RealEstateForRentCreationDto) dto).setContractYears(((RealEstateForRent) entity).getContractYears());
        
        dto.setContractType(ContractType.FOR_RENT);
    }


    
    @Override
    public boolean supports(RealEstate realEstate) 
    {
        return RealEstateForRent.class.isAssignableFrom(realEstate.getClass());
    }
}