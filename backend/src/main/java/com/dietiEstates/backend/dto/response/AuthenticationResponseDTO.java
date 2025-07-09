
package com.dietiEstates.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO 
{
    private String jwtToken;
    private Boolean mustChangePassword;
}