
package com.dietiestates.backend.factory;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.model.entity.RealEstateForRent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateRootForRentFactory implements RealEstateRootFactory<RealEstateForRent> 
{
    @Override
    public Root<RealEstateForRent> create(CriteriaQuery<?> query) 
    {
        return query.from(RealEstateForRent.class);
    }
    
    @Override
    public boolean supports(ContractType contractType) 
    {
        return ContractType.FOR_RENT == contractType;
    }
}