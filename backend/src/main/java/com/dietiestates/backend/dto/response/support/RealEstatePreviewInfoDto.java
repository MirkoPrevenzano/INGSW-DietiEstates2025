
package com.dietiestates.backend.dto.response.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che contiene le informazioni essenziali di un annuncio immobiliare, che saranno poi riportate in un'anteprima.")
public class RealEstatePreviewInfoDto
{  
    @Schema(description = "Identificativo univoco dell'immobile.", example = "101")
    private long id;

    @Schema(description = "Titolo dell'annuncio immobiliare.", example = "Appartamento luminoso nel centro storico.")
    private String title;

    private String description;

    @Schema(description = "Prezzo richiesto per l'immobile, espresso in euro.", example = "250000")
    private double price;

    @Schema(description = "Indirizzo o via in cui si trova l'immobile.", example = "Via Toledo 45, Napoli")
    private String street;

    @Schema(description = "Longitudine della posizione dell'immobile.", example = "14,251222")
    private double longitude;
    
    @Schema(description = "Latitudine geografica della posizione dell'immobile.", example = "40,851775")
    private double latitude; 
}