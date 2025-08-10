
package com.dietiEstates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

import com.dietiEstates.backend.validator.EmailValidator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorCreationDto 
{
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @EmailValidator
    private String username;
}