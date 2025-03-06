
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateForSaleCreationDTO extends RealEstateCreationDTO
{
    @NonNull
    private String notaryDeedState;   
}
