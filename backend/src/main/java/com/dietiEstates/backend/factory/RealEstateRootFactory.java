
package com.dietiEstates.backend.factory;
 
import com.dietiEstates.backend.model.RealEstateForRent;
import com.dietiEstates.backend.model.RealEstateForSale;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;



public final class RealEstateRootFactory 
{
    private RealEstateRootFactory() {};
    
   public static Root<?> createRealEstateRootFromType(String realEstateType, CriteriaQuery<?> criteriaQuery)
   {
        if(realEstateType.equals("For Sale"))
            return criteriaQuery.from(RealEstateForSale.class);
        else if(realEstateType.equals("For Rent"))
            return criteriaQuery.from(RealEstateForRent.class);
        else
            return null;
   }
}