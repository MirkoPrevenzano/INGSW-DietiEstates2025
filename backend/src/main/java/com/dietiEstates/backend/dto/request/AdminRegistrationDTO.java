
package com.dietiEstates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AdminRegistrationDTO 
{
    //@NonNull
    //@NotBlank
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String agencyName;

    @NonNull
    private String businessName;

    @NonNull
    @Size(min = 2)
    private String vatNumber;
}