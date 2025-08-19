
package com.dietiestates.backend.factory;

import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.resolver.Supportable;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


public interface RealEstateRootFactory extends Supportable<ContractType>
{
    Root<? extends RealEstate> create(CriteriaQuery<?> query);
}