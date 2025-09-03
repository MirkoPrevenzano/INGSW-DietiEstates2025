
package com.dietiestates.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che rappresenta la risposta restituita dopo un'autenticazione avvenuta con successo.")
public class AuthenticationResponseDto 
{
    @Schema(description = "Token JWT generato al momento dell'autenticazione, utilizzato per autorizzare le richieste successive.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String jwtToken;
}