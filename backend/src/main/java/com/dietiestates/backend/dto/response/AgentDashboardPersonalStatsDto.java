
package com.dietiestates.backend.dto.response;

import com.dietiestates.backend.dto.AgentStatsDto;

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