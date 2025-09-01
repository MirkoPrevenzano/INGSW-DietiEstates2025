
package com.dietiestates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO generico della classe AgentStats, che contiene diverse statistiche relative ad un agente.")
public class AgentStatsDto 
{
    @Schema(description = "Numero totale di annunci immobiliari caricati dall'agente.", example = "21")
    private int totalUploadedRealEstates;

    @Schema(description = "Numero totale di immobili venduti dall'agente.", example = "9")
    private int totalSoldRealEstates;    

    @Schema(description = "Numero totale di immobili venduti (in affitto) dall'agente.", example = "12")
    private int totalRentedRealEstates;

    @Schema(description = "Numero totale di incassi relativi alle vendite di immobili da parte dall'agente.", example = "2500000")
    private double salesIncome;
    
    @Schema(description = "Numero totale di incassi relativi alle vendite (in affitto) di immobili da parte dall'agente.", example = "500000")
    private double rentalsIncome; 
}