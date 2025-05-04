
package com.dietiEstates.backend.factory;
 
import org.springframework.stereotype.Component;
import com.dietiEstates.backend.model.RealEstateForRent;
import com.dietiEstates.backend.model.RealEstateForSale;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public class RealEstateRootFactory 
{
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