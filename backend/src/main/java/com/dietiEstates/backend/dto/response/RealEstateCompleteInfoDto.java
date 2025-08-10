
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.response.support.AgentPublicInfoDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateCompleteInfoDto
{
    private RealEstateCreationDto realEstateCreationDto;

    private AgentPublicInfoDto agentPublicInfoDto;    
}