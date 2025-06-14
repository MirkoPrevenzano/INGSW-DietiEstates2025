
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateCompleteDTO
{
    @NonNull
    private RealEstateCreationDTO realEstateCreationDTO;

    @NonNull
    private AgentPublicInfoDTO agentPublicInfoDTO;    
}