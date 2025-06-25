
package com.dietiEstates.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AuthenticationResponseDTO 
{
    @NonNull
    private String jwtToken;

    @NonNull
    private Boolean mustChangePassword;
}