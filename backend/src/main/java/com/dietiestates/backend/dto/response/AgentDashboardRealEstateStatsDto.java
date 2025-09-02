
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
@Schema(description = "DTO che rappresenta le statistiche relative a un immobile gestito da un agente immobiliare, da visualizzare in una dashboard.")
public class AgentDashboardRealEstateStatsDto 
{
    @Schema(description = "Identificativo univoco dell'immobile.", example = "1024")
    private long id;

    @Schema(description = "Titolo dell'annuncio immobiliare.", example = "Appartamento trilocale con balcone panoramico")
    private String title;

    @Schema(description = "Data e ora di pubblicazione dell'annuncio.", example = "2025-06-15, 10:30:00")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss")
    private LocalDateTime uploadingDate;

    @Schema(description = "Numero totale di visualizzazioni ricevute dall'annuncio.", example = "356")
    private long viewsNumber;

    @Schema(description = "Numero di visite fisiche effettuate sull'immobile.", example = "3")
    private long visitsNumber;

    @Schema(description = "Numero di offerte ricevute per l'immobile.", example = "12")
    private long offersNumber;
}