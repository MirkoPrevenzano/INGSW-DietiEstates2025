
package com.dietiEstates.backend.mapper;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;


@Component
public class RealEstateCreationForSaleDTOMapper extends RealEstateCreationDTOMapper
{
    @Override
    public RealEstateForSaleCreationDTO toDto(RealEstate entity) 
    {
        RealEstateForSale realEstateForSale = (RealEstateForSale) entity;
        RealEstateForSaleCreationDTO realEstateForSaleCreationDTO = new RealEstateForSaleCreationDTO();

        super.mapCommonFieldsToDto(realEstateForSale, realEstateForSaleCreationDTO);

        realEstateForSaleCreationDTO.setNotaryDeedState(realEstateForSale.getNotaryDeedState());

        return realEstateForSaleCreationDTO;
    }


    @Override
    public RealEstateForSale toEntity(RealEstateCreationDTO dto) 
    {
        RealEstateForSaleCreationDTO realEstateForSaleCreationDTO = (RealEstateForSaleCreationDTO) dto;
        RealEstateForSale realEstateForSale = new RealEstateForSale();

        super.mapCommonFieldsToEntity(realEstateForSaleCreationDTO, realEstateForSale);

        realEstateForSale.setNotaryDeedState(realEstateForSaleCreationDTO.getNotaryDeedState());

        return realEstateForSale;
    }


    @Override
    public boolean supports(Class<? extends RealEstate> clazz) 
    {
        return RealEstateForSale.class.isAssignableFrom(clazz);
    }
}