
package com.dietiEstates.backend.factory;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.model.entity.RealEstateForSale;

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