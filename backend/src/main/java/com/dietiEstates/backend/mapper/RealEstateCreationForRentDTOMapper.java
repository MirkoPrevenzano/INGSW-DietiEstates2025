
package com.dietiEstates.backend.mapper;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.enums.ContractType;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;


@Component
public class RealEstateCreationForRentDTOMapper extends RealEstateCreationDTOMapper
{
    @Override
    protected RealEstateForRentCreationDTO initializeDto() 
    {
        return new RealEstateForRentCreationDTO();
    }


    @Override
    protected RealEstateForRent initializeEntity() 
    {
        return new RealEstateForRent();
    }


    @Override
    protected void mapSpecificFieldsToEntity(RealEstateCreationDTO dto, RealEstate entity) 
    {
        ((RealEstateForRent) entity).setSecurityDeposit(((RealEstateForRentCreationDTO) dto).getSecurityDeposit());
        ((RealEstateForRent) entity).setContractYears(((RealEstateForRentCreationDTO) dto).getContractYears());
    }


    @Override
    protected void mapSpecificFieldstoDto(RealEstate entity, RealEstateCreationDTO dto) 
    {
        ((RealEstateForRentCreationDTO) dto).setSecurityDeposit(((RealEstateForRent) entity).getSecurityDeposit());
        ((RealEstateForRentCreationDTO) dto).setContractYears(((RealEstateForRent) entity).getContractYears());
        ((RealEstateForRentCreationDTO) dto).setContractType(ContractType.FOR_RENT);
    }


    @Override
    public boolean supports(RealEstate realEstate) 
    {
        return RealEstateForRent.class.isAssignableFrom(realEstate.getClass());
    }
}