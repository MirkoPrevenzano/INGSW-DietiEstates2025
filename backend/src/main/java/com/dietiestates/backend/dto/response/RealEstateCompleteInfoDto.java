
package com.dietiestates.backend.dto.response;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;

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