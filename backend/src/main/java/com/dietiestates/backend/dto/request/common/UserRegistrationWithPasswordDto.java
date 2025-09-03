
package com.dietiestates.backend.dto.request.common;

import jakarta.validation.constraints.NotBlank;

import com.dietiestates.backend.validator.PasswordValidator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Schema(description = "DTO astratto per la registrazione di un generico utente con credenziali complete, che include, oltre ai dati anagrafici e all'username, anche la password.")
public abstract class UserRegistrationWithPasswordDto extends UserRegistrationDto 
{
    @NotBlank
    @PasswordValidator
    @Schema(description = "Password scelta dall'utente. Deve rispettare i criteri di sicurezza definiti dal sistema (lunghezza minima, presenza di caratteri speciali, ecc...).", example = "P@ssw0rd123!")
    private String password;


    
    protected UserRegistrationWithPasswordDto(String name, String surname, String username, String password) 
    {
        super(name, surname, username);
        this.password = password;
    } 
}