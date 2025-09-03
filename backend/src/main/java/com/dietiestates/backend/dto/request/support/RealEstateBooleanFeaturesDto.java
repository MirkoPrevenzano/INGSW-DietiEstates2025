
package com.dietiestates.backend.dto.request.support;

import jakarta.validation.constraints.NotNull;

import com.dietiestates.backend.validator.groups.OnCreate;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che rappresenta le caratteristiche booleane (si/no) dell'immobile, come la presenza di giardino o garage.")
public class RealEstateBooleanFeaturesDto 
{
    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di aria condizionata.", example = "true")
    private Boolean airConditioning;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di riscaldamento.", example = "true")
    private Boolean heating;  

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di ascensore.", example = "false")
    private Boolean elevator;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di portineria.", example = "true")
    private Boolean concierge;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di terrazza.", example = "true")
    private Boolean terrace;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di garage.", example = "true")
    private Boolean garage;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di balcone.", example = "true")
    private Boolean balcony;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di giardino.", example = "false")
    private Boolean garden;

    @NotNull(groups = OnCreate.class)
    @Schema(description = "Indica se l'immobile dispone di piscina.", example = "false")
    private Boolean swimmingPool;
}