
package com.dietiEstates.backend.dto.request;

import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UpdatePasswordDTO 
{
    @NonNull
    private String oldPassword;

    @NonNull
    private String newPassword;
}