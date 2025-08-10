
package com.dietiEstates.backend.mapper;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDto;
import com.dietiEstates.backend.enums.ContractType;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;


@Component
public class RealEstateCreationForRentDTOMapper extends RealEstateCreationDtoMapper
{
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
        ((RealEstateCreationDto) dto).setContractType(ContractType.FOR_RENT);
    }


    @Override
    public boolean supports(RealEstate realEstate) 
    {
        return RealEstateForRent.class.isAssignableFrom(realEstate.getClass());
    }
}