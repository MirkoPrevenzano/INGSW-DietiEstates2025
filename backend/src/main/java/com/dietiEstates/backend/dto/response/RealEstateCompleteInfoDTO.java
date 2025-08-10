
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.response.support.AgentPublicInfoDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateCompleteInfoDTO
{
    private RealEstateCreationDTO realEstateCreation;

    private AgentPublicInfoDTO agentPublicInfo;    
}