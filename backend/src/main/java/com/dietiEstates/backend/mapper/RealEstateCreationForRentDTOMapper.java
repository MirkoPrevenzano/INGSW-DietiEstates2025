
package com.dietiEstates.backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstateForRent;

import lombok.RequiredArgsConstructor;


@Component
public class RealEstateCreationForRentDTOMapper extends RealEstateCreationDTOMapperr<RealEstateForRentCreationDTO, RealEstateForRent>
{
    @Override
    public RealEstateForRentCreationDTO toDto(RealEstateForRent entity) 
    {
        RealEstateForRentCreationDTO realEstateForRentCreationDTO = new RealEstateForRentCreationDTO();

        super.mapCommonFieldsToDto(entity, realEstateForRentCreationDTO);

        realEstateForRentCreationDTO.setSecurityDeposit(entity.getSecurityDeposit());
        realEstateForRentCreationDTO.setContractYears(entity.getContractYears());

        return realEstateForRentCreationDTO;
    }


    @Override
    public RealEstateForRent toEntity(RealEstateForRentCreationDTO dto) 
    {
        RealEstateForRent realEstateForRent = new RealEstateForRent();

        super.mapCommonFieldsToEntity(dto, realEstateForRent);

        realEstateForRent.setSecurityDeposit(dto.getSecurityDeposit());
        realEstateForRent.setContractYears(dto.getContractYears());

        return realEstateForRent;
    }   
}