
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.dto.response.support.AgentStatsDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDashboardPersonalStatsDTO
{
    private AgentStatsDTO agentStatsDTO;

    private Integer[] monthlyDeals;
}