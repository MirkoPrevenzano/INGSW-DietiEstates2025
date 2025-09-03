
package com.dietiestates.backend.dto.request.interfaces;

import jakarta.validation.constraints.NotBlank;

import com.dietiestates.backend.validator.EmailValidator;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO astratto per la registrazione di un generico utente, contenente le informazioni anagrafiche e l'identificativo univoco (username).")
public abstract class UserRegistrationDto 
{
    @NotBlank
    @Schema(description = "Nome dell'utente.", example = "Mario")
    private String name;

    @NotBlank
    @Schema(description = "Cognome dell'utente", example = "Rossi")
    private String surname;

    @NotBlank
    @EmailValidator
    @Schema(description = "Indirizzo email utilizzato come username univoco dell'utente.", example = "mario.rossi@example.com")
    private String username;
}