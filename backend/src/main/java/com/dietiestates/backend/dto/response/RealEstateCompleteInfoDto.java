
package com.dietiestates.backend.dto.response;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che rappresenta le informazioni complete relative ad un annuncio immobiliare, comprensive dei dati del suo agente.")
public class RealEstateCompleteInfoDto
{
    private RealEstateCreationDto realEstateCreationDto;

    private AgentPublicInfoDto agentPublicInfoDto;    
}