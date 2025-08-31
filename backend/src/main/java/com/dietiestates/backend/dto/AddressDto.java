
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
@Schema(description = "DTO di un Address")
public class AddressDto 
{
    @Schema(description = "Nome di Stato/Nazione", example = "Itala")
    @NotBlank(groups = OnCreate.class)
    private String state;

    @Schema(description = "Nome di regione/paese", example = "Campania")
    @NotBlank(groups = OnCreate.class)
    private String country;

    @Schema(description = "Nome di città", example = "Napoli")
    @NotBlank(groups = OnCreate.class)
    private String city;

    @Schema(description = "Nome della via", example = "Corso San Giovanni")
    @NotBlank(groups = OnCreate.class)
    private String street;

    @Schema(description = "Valore del codice postale", example = "80146")
    @NotBlank(groups = OnCreate.class)
    @Pattern(regexp = "^\\d{5}$", message = "must be of 5 digits")
    private String postalCode;

    @Schema(description = "Valore del numero", example = "1090")
    @NotNull(groups = OnCreate.class)
    private String houseNumber;

    @Schema(description = "Valore della longitudine", example = "120")
    @NotNull(groups = OnCreate.class)
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double longitude;

    @Schema(description = "Valore della longitudine", example = "120")
    @NotNull(groups = OnCreate.class)
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double latitude;
}