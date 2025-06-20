
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;

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

    @NonNull
    private AgentPublicInfoDTO agentPublicInfoDTO;    
}