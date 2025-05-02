
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateCompleteInfoDTO
{
    @NonNull
    private RealEstateCreationDTO realEstateCreationDTO;
    private AgentInfoDTO agentInfoDTO;    
}