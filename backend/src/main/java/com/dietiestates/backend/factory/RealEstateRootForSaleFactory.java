
package com.dietiestates.backend.factory;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.model.entity.RealEstateForSale;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateRootForSaleFactory implements RealEstateRootFactory<RealEstateForSale>
{
    @Override
    public Root<RealEstateForSale> create(CriteriaQuery<?> query) 
    {
        return query.from(RealEstateForSale.class);
    }

    
    @Override
    public boolean supports(ContractType contractType) 
    {
        return ContractType.FOR_SALE == contractType;
    }
}