
package com.dietiEstates.backend.factory;

import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.resolver.Supportable;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


public interface RealEstateRootFactory 
{
    Root<? extends RealEstate> create(CriteriaQuery<?> query);
    public boolean supports(String realEstateType);
}