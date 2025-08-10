
package com.dietiEstates.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.dietiEstates.backend.dto.request.support.AddressDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDTO;
import com.dietiEstates.backend.enums.ContractType;

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
    {@JsonSubTypes.Type(value = RealEstateForSaleCreationDTO.class, name = "For Sale"),
     @JsonSubTypes.Type(value = RealEstateForRentCreationDTO.class, name = "For Rent")})
public class RealEstateCreationDTO
{
    @NotNull
    private ContractType contractType;

    @Valid
    private AddressDTO addressDTO;

    @Valid
    private RealEstateMainFeaturesDTO realEstateMainFeaturesDTO;

    @Valid
    private RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO;

    @Valid
    private RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO;
}