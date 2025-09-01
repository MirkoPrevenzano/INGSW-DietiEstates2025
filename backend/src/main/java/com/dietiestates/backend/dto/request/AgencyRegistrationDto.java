
package com.dietiestates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

import com.dietiestates.backend.dto.request.interfaces.UserRegistrationWithPasswordDto;
import com.dietiestates.backend.validator.VatNumberValidator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Schema(description = "DTO per la registrazione di un'agenzia, contenente i dati dell'amministratore responsabile (che sarà anch'esso registrato) e le informazioni identificative dell'agenzia stessa.")
public class AgencyRegistrationDto extends UserRegistrationWithPasswordDto
{
    @NotBlank
    @Schema(description = "Denominazione ufficiale dell'agenzia.", example = "Studio Immobiliare Rossi")
    private String agencyName;

    @NotBlank
    @Schema(description = "Ragione sociale dell'agenzia.", example = "Studio Immobiliare Rossi S.r.l.")
    private String businessName;

    @NotBlank
    @VatNumberValidator
    @Schema(description = "Numero di partita IVA associato all'agenzia.", example = "01234567890")
    private String vatNumber;

    
    public AgencyRegistrationDto(String name, String surname, String username, String password, String agencyName, String businessName, String vatNumber) 
    {
        super(name, surname, username, password);
        this.agencyName = agencyName;
        this.businessName = businessName;
        this.vatNumber = vatNumber;
    }
}