
package com.dietiEstates.backend.dto.response;

import com.dietiEstates.backend.model.embeddable.AgentStats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentStatsDTO
{
    private AgentStats agentStats;
    private Integer[] estatesPerMonths;
}