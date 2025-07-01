
package com.dietiEstates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
//@RequiredArgsConstructor
public class AdminRegistrationDTO 
{
    //@NonNull
    @NotNull
    //@NotBlank
    private String name;

    private String surname;

    private String username;

    private String password;

    private String agencyName;

    private String businessName;

    @Size(min = 2)
    private String vatNumber;
}