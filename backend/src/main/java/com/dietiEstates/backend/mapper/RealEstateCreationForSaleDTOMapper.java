
package com.dietiEstates.backend.mapper;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstateForSale;


@Component
public class RealEstateCreationForSaleDTOMapper extends RealEstateCreationDTOMapperr<RealEstateForSaleCreationDTO, RealEstateForSale>
{
    @Override
    public RealEstateForSaleCreationDTO toDto(RealEstateForSale entity) 
    {
        RealEstateForSaleCreationDTO realEstateForSaleCreationDTO = new RealEstateForSaleCreationDTO();

        super.mapCommonFieldsToDto(entity, realEstateForSaleCreationDTO);

        realEstateForSaleCreationDTO.setNotaryDeedState(entity.getNotaryDeedState());

        return realEstateForSaleCreationDTO;
    }


    @Override
    public RealEstateForSale toEntity(RealEstateForSaleCreationDTO dto) 
    {
        RealEstateForSale realEstateForSale = new RealEstateForSale();

        super.mapCommonFieldsToEntity(dto, realEstateForSale);

        realEstateForSale.setNotaryDeedState(dto.getNotaryDeedState());

        return realEstateForSale;
    }
}