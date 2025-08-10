
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.dto.response.support.AgentStatsDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDashboardPersonalStatsDto
{
    private AgentStatsDto agentStatsDto;

    private Integer[] monthlyDeals;
}