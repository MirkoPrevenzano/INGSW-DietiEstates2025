
package com.dietiestates.backend.dto.request.support;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che rappresenta le caratteristiche di posizione dell'immobile, come vicinanza a parchi, scuole o fermate di trasporti pubblici.")
public class RealEstateLocationFeaturesDto
{
    @NotNull
    @Schema(description = "Indica se l'immobile è vicino a un parco.", example = "true")
    private Boolean nearPark;

    @NotNull
    @Schema(description = "Indica se l'immobile è vicino a una scuola.", example = "true")
    private Boolean nearSchool;
    
    @NotNull
    @Schema(description = "Indica se l'immobile è vicino a una fermata di trasporto pubblico.", example = "true")
    private Boolean nearPublicTransport;   
}