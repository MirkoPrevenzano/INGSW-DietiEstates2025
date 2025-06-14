
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.model.embeddable.AgentStats;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AgentStatsDTO
{
    @NonNull
    private AgentStats agentStats;

    @NonNull
    private Integer[] estatesPerMonths;
}