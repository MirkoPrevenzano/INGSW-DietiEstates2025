
package com.dietiEstates.backend.dto;

import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class OldNewPasswordDTO 
{
    @NonNull
    private String oldPassword;

    @NonNull
    private String newPassword;
}