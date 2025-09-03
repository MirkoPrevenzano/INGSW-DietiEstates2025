
package com.dietiestates.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.dietiestates.backend.dto.AddressDto;
import com.dietiestates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiestates.backend.enums.ContractType;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "contractType",
    visible = true)
@JsonSubTypes(
    {@JsonSubTypes.Type(value = RealEstateForSaleCreationDto.class, name = "For Sale"),
     @JsonSubTypes.Type(value = RealEstateForRentCreationDto.class, name = "For Rent")})
@Schema(description = "DTO astratto per la creazione di un annuncio immobiliare, contenente informazioni sul tipo di contratto, sull'indirizzo e sulle diverse caratteristiche dell'immobile.")
public class RealEstateCreationDto
{
    @NotNull
    @Schema(description = "Tipo di contratto dell'immobile.", example = "For Sale")
    private ContractType contractType;

    @Valid
    private AddressDto addressDto;

    @Valid
    private RealEstateMainFeaturesDto realEstateMainFeaturesDto;

    @Valid
    private RealEstateBooleanFeaturesDto realEstateBooleanFeaturesDto;

    @Valid
    private RealEstateLocationFeaturesDto realEstateLocationFeaturesDto;
}