
package com.dietiEstates.backend.factory;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.model.entity.RealEstateForRent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateRootForRentFactory implements RealEstateRootFactory 
{
    @Override
    public Root<RealEstateForRent> create(CriteriaQuery<?> query) 
    {
        return query.from(RealEstateForRent.class);
    }
    

    @Override
    public boolean supports(String realEstateType) 
    {
        return realEstateType.equals("For Rent");
    }
}