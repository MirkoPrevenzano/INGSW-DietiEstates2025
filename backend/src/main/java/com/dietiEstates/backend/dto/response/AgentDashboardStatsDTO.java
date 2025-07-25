
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.dto.response.support.RealEstateStatsDTO;

import java.util.List;

import com.dietiEstates.backend.dto.response.support.AgentStatsDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDashboardStatsDTO
{
    private AgentStatsDTO agentStatsDTO;
    //private List<RealEstateStatsDTO> realEstateStatsDTOs;
    private Integer[] estatesPerMonths;
}