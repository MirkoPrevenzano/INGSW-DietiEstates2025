
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateCompleteInfoDTO
{
    private RealEstateCreationDTO realEstateCreationDTO;
    private AgentPublicInfoDTO agentPublicInfoDTO;    
}