
package com.dietiestates.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Nome utente dell'agente (email), utilizzato per l'accesso al sistema.", example = "lucarossi92@gmail.com")
    private String username;
    
    @Schema(description = "Nome dell'agenzia immobiliare per cui lavora l'agente.", example = "Agenzia Immobiliare Milano Centro")
    private String agencyName;
}