
package com.dietiEstates.backend.factory;
 
import com.dietiEstates.backend.dto.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.util.RealEstateMappingUtil;



//@Component
//@RequiredArgsConstructor
public final class RealEstateFactory 
{
    //private final RealEstateMappingUtil realEstateMappingUtil;
    
    private RealEstateFactory() {};

    
    public static RealEstate createFromDTO(RealEstateCreationDTO realEstateCreationDTO) 
    {
        if(realEstateCreationDTO instanceof RealEstateForRentCreationDTO)
            return RealEstateMappingUtil.realEstateForRentMapper((RealEstateForRentCreationDTO) realEstateCreationDTO);
        else if(realEstateCreationDTO instanceof RealEstateForSaleCreationDTO)
            return RealEstateMappingUtil.realEstateForSaleMapper((RealEstateForSaleCreationDTO) realEstateCreationDTO);
        else
            return null;
    };
}