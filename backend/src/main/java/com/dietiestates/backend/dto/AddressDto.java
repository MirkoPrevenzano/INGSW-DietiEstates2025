
package com.dietiestates.backend.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.dietiestates.backend.validator.groups.OnCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che contiene informazioni sul luogo preciso di un immobile.")
public class AddressDto 
{
    @Schema(description = "Nome dello Stato/Nazione.", example = "Itala")
    @NotBlank(groups = OnCreate.class)
    private String state;

    @Schema(description = "Nome della regione/paese.", example = "Campania")
    @NotBlank(groups = OnCreate.class)
    private String country;

    @Schema(description = "Nome della città.", example = "Napoli")
    @NotBlank(groups = OnCreate.class)
    private String city;

    @Schema(description = "Nome della via.", example = "Corso San Giovanni")
    @NotBlank(groups = OnCreate.class)
    private String street;

    @Schema(description = "Valore del codice postale.", example = "80146")
    @NotBlank(groups = OnCreate.class)
    @Pattern(regexp = "^\\d{5}$", message = "must be of 5 digits")
    private String postalCode;

    @Schema(description = "Valore del numero civico.", example = "1090")
    @NotNull(groups = OnCreate.class)
    private String houseNumber;

    @Schema(description = "Valore della longitudine in gradi.", example = "14.3056")
    @NotNull(groups = OnCreate.class)
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double longitude;

    @Schema(description = "Valore della latitudine in gradi.", example = "40.8522")
    @NotNull(groups = OnCreate.class)
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double latitude;
}