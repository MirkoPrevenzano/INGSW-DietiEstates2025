
package com.dietiEstates.backend.factory;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.mapper.RealEstateCreationForSaleDTOMapper;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForSale;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateRootForSaleFactory implements RealEstateRootFactory 
{
    @Override
    public Root<RealEstateForSale> create(CriteriaQuery<?> query) 
    {
        return query.from(RealEstateForSale.class);
    }

    @Override
    public boolean supports(String realEstateType) 
    {
        return realEstateType.equals("For Sale");
    }
}