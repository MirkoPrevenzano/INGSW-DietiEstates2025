
package com.dietiEstates.backend.mapper;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.enums.ContractType;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForSale;


@Component
public class RealEstateCreationForSaleDTOMapper extends RealEstateCreationDTOMapper
{
    @Override
    protected RealEstateForSaleCreationDTO initializeDto() 
    {
        return new RealEstateForSaleCreationDTO();
    }


    @Override
    protected RealEstateForSale initializeEntity() 
    {
        return new RealEstateForSale();
    }


    @Override
    protected void mapSpecificFieldsToEntity(RealEstateCreationDTO dto, RealEstate entity) 
    {
        ((RealEstateForSale) entity).setNotaryDeedState(((RealEstateForSaleCreationDTO) dto).getNotaryDeedState());
    }


    @Override
    protected void mapSpecificFieldstoDto(RealEstate entity, RealEstateCreationDTO dto) 
    {
        ((RealEstateForSaleCreationDTO) dto).setNotaryDeedState(((RealEstateForSale) entity).getNotaryDeedState());
        ((RealEstateForSaleCreationDTO) dto).setContractType(ContractType.FOR_SALE);
    }


    @Override
    public boolean supports(RealEstate realEstate) 
    {
        return RealEstateForSale.class.isAssignableFrom(realEstate.getClass());
    }
}