
package com.dietiEstates.backend.factory;
 
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


//@Utili
public final class RealEstateRootFactory 
{
    private RealEstateRootFactory() {};
    

    public static Root<?> createFromType(String realEstateType, CriteriaQuery<?> criteriaQuery)
    {
            if(realEstateType.equals("For Sale"))
                return criteriaQuery.from(RealEstateForSale.class);
            else if(realEstateType.equals("For Rent"))
                return criteriaQuery.from(RealEstateForRent.class);
            else
                return null;
    }
}