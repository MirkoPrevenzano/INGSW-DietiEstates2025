
package com.dietiestates.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.dietiestates.backend.dto.response.support.AgentStatsDto;

import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDashboardPersonalStatsDto
{
    private AgentStatsDto agentStatsDto;

    private Integer[] monthlyDeals;
}