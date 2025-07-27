
package com.dietiEstates.backend.factory;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.dietiEstates.backend.enums.ContractType;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.resolver.Supportable;


public interface RealEstateRootFactory extends Supportable<ContractType>
{
    Root<? extends RealEstate> create(CriteriaQuery<?> query);
}