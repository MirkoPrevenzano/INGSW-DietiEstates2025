
package com.dietiestates.backend.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che contiene alcune informazioni relative ad un annuncio immobiliare recentemente pubblicato da un agente.")
public class AgentRecentRealEstateDto 
{
    @Schema(description = "Identificativo univoco dell'immobile.", example = "20")
    private long id;

    @Schema(description = "Titolo dell'annuncio immobiliare.", example = "Bilocale ristrutturato in centro storico")
    private String title;
    
    private String description;
    
    @Schema(description = "Data e ora di pubblicazione dell'annuncio.", example = "2025-08-20, 15:45:00")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd', 'HH:mm:ss")
    private LocalDateTime uploadingDate;
}