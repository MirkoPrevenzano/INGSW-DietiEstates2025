
package com.dietiEstates.backend.dto.request;

import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDTO 
{
    @NonNull
    private String oldPassword;

    @NonNull
    private String newPassword;
}