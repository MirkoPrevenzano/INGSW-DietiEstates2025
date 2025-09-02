
package com.dietiestates.backend.dto.response;

import com.dietiestates.backend.dto.AgentStatsDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che rappresenta le statistiche personali di un agente, da visualizzare in una dashboard.")
public class AgentDashboardPersonalStatsDto
{
    private AgentStatsDto agentStatsDto;

    @Schema(description = "Numero di affari conclusi dall'agente per ciascun mese dell'anno corrente.", example = "[2, 1, 0, 3, 4, 2, 5, 1, 0, 2, 3, 4]")
    private Integer[] monthlyDeals;
}