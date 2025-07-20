
package com.dietiEstates.backend.dto.request;

import com.dietiEstates.backend.validator.EmailValidator;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentRegistrationDTO 
{
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @EmailValidator
    private String username;
}