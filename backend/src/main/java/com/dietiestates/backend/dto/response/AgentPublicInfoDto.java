
package com.dietiestates.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO che rappresenta le informazioni pubbliche relative a un agente immobiliare.")
public class AgentPublicInfoDto 
{
    @Schema(description = "Nome dell'agente.", example = "Luca")
    private String name;

    @Schema(description = "Cognome dell'agente.", example = "Rossi")
    private String surname;

    @Schema(description = "Nome utente dell'agente (email).", example = "lucarossi92@gmail.com")
    private String username;
    
    @Schema(description = "Nome dell'agenzia immobiliare per cui lavora l'agente.", example = "Agenzia Immobiliare Milano Centro")
    private String agencyName;
}