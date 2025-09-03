
package com.dietiestates.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import com.dietiestates.backend.dto.AddressDto;
import com.dietiestates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiestates.backend.enums.ContractType;
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
@Schema(description = "DTO per la creazione di un annuncio immobiliare in affitto, contenente informazioni sul tipo di contratto, sull'indirizzo e sulle diverse caratteristiche dell'immobile.")
public class RealEstateForRentCreationDto extends RealEstateCreationDto
{
    @NotNull(groups = OnCreate.class)
    @PositiveOrZero
    @Schema(description = "Importo del deposito cauzionale richiesto per l'affitto, in euro.", example = "1500")
    private Double securityDeposit;

    @NotNull(groups = OnCreate.class)
    @PositiveOrZero
    @Schema(description = "Durata del contratto di locazione, espressa in anni.", example = "4")
    private Integer contractYears;



    public RealEstateForRentCreationDto(ContractType contractType, AddressDto addressDto, RealEstateMainFeaturesDto realEstateMainFeaturesDto, 
                                        RealEstateBooleanFeaturesDto realEstateBooleanFeaturesDto, RealEstateLocationFeaturesDto realEstateLocationFeaturesDto, 
                                        Double securityDeposit, Integer contractYears)
    {
        super(contractType, addressDto, realEstateMainFeaturesDto, realEstateBooleanFeaturesDto, realEstateLocationFeaturesDto);
        this.securityDeposit = securityDeposit;
        this.contractYears = contractYears;
    }
}