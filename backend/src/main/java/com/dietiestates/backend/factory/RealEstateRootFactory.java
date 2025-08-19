
package com.dietiestates.backend.factory;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.resolver.Supportable;


public interface RealEstateRootFactory<R extends RealEstate> extends Supportable<ContractType>
{
    Root<R> create(CriteriaQuery<?> query);
}