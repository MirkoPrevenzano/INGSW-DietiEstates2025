
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.response.support.AgentPublicInfoDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateCompleteInfoDTO
{
    private RealEstateCreationDto realEstateCreation;

    private AgentPublicInfoDTO agentPublicInfo;    
}