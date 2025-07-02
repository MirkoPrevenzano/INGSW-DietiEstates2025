
package com.dietiEstates.backend.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationDTO 
{
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}