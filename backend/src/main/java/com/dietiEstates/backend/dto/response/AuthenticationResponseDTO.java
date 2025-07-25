
package com.dietiEstates.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO 
{
    private String jwtToken;
}