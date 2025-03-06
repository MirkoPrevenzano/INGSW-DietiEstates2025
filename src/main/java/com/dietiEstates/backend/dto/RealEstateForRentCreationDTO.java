
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
public class RealEstateForRentCreationDTO extends RealEstateCreationDTO
{
    @NonNull
    private Double securityDeposit;

    @NonNull
    private Integer contractYears;
}
