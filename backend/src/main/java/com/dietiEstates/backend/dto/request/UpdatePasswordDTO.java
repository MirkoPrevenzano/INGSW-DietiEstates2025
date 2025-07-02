
package com.dietiEstates.backend.dto.request;

import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
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