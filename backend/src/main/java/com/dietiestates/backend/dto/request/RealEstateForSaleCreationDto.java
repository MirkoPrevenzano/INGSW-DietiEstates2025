
package com.dietiestates.backend.dto.request;

import jakarta.validation.constraints.NotNull;

import com.dietiestates.backend.dto.AddressDto;
import com.dietiestates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.enums.NotaryDeedState;
import com.dietiestates.backend.validator.groups.OnCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Schema(description = "DTO per la creazione di un annuncio immobiliare in vendita, contenente informazioni sul tipo di contratto, sull'indirizzo e sulle diverse caratteristiche dell'immobile.")
public class RealEstateForSaleCreationDto extends RealEstateCreationDto
{
    @NotNull(groups = OnCreate.class)
    @Schema(description = "Stato dell'atto notarile relativo alla compravendita dell'immobile.", example = "Free")
    private NotaryDeedState notaryDeedState;


    public RealEstateForSaleCreationDto(ContractType contractType, AddressDto addressDto, RealEstateMainFeaturesDto realEstateMainFeaturesDto, 
                                        RealEstateBooleanFeaturesDto realEstateBooleanFeaturesDto, RealEstateLocationFeaturesDto realEstateLocationFeaturesDto, 
                                        NotaryDeedState notaryDeedState)
    {
        super(contractType, addressDto, realEstateMainFeaturesDto, realEstateBooleanFeaturesDto, realEstateLocationFeaturesDto);
        this.notaryDeedState = notaryDeedState;
    }
}