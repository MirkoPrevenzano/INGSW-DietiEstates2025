
package com.dietiEstates.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDTO 
{
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}