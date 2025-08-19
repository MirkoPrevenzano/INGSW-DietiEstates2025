
package com.dietiestates.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.dietiestates.backend.dto.request.support.AddressDto;
import com.dietiestates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiestates.backend.enums.ContractType;
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
public class RealEstateCreationDto
{
    @NotNull
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