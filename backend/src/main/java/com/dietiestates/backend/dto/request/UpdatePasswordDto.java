
package com.dietiestates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

import com.dietiestates.backend.validator.PasswordValidator;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO per la modifica della password di un utente, contenente la password attuale e quella nuova.")
public class UpdatePasswordDto 
{
    @NotBlank
    @Schema(description = "Password attuale dell'utente.", example = "OldP@ssw0rd123!")
    private String oldPassword;

    @NotBlank
    @PasswordValidator
    @Schema(description = "Nuova password dall'utente.", example = "NewP@ssw0rd456!")
    private String newPassword;
}