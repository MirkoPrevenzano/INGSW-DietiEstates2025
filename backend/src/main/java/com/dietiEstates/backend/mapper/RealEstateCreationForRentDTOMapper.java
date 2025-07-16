
package com.dietiEstates.backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;

import lombok.RequiredArgsConstructor;


@Component
public class RealEstateCreationForRentDTOMapper extends RealEstateCreationDTOMapper
{
    @Override
    public RealEstateForRentCreationDTO toDto(RealEstate entity) 
    {
        RealEstateForRent realEstateForRent = (RealEstateForRent) entity;
        RealEstateForRentCreationDTO realEstateForRentCreationDTO = new RealEstateForRentCreationDTO();

        super.mapCommonFieldsToDto(realEstateForRent, realEstateForRentCreationDTO);

        realEstateForRentCreationDTO.setSecurityDeposit(realEstateForRent.getSecurityDeposit());
        realEstateForRentCreationDTO.setContractYears(realEstateForRent.getContractYears());

        return realEstateForRentCreationDTO;
    }


    @Override
    public RealEstateForRent toEntity(RealEstateCreationDTO dto) 
    {
        RealEstateForRentCreationDTO realEstateForRentCreationDTO = (RealEstateForRentCreationDTO) dto;
        RealEstateForRent realEstateForRent = new RealEstateForRent();

        super.mapCommonFieldsToEntity(realEstateForRentCreationDTO, realEstateForRent);

        realEstateForRent.setSecurityDeposit(realEstateForRentCreationDTO.getSecurityDeposit());
        realEstateForRent.setContractYears(realEstateForRentCreationDTO.getContractYears());

        return realEstateForRent;
    }   


    @Override
    public boolean supports(RealEstate realEstate) 
    {
        return RealEstateForRent.class.isAssignableFrom(realEstate.getClass());
    }
}