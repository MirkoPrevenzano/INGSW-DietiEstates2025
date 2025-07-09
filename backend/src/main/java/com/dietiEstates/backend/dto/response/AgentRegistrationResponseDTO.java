
package com.dietiEstates.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentRegistrationResponseDTO 
{
    private String username;
    private String temporaryPassword;
}