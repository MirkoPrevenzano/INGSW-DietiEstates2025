
package com.dietiEstates.backend.dto;

import java.time.LocalDateTime;

import com.dietiEstates.backend.model.embeddable.ExternalRealEstateFeatures;
import com.dietiEstates.backend.model.embeddable.InternalRealEstateFeatures;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateCompleteDTO 
{
    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private Double price;

    @NonNull
    private LocalDateTime uploadingDate;
    
    @NonNull
    private Double condoFee;

    @NonNull
    private String energyClass;

    @NonNull
    private InternalRealEstateFeatures internalFeatures;

    @NonNull
    private ExternalRealEstateFeatures externalFeatures;    
    
    @NonNull
    private AddressDTO address;

    @NonNull
    private UserDTO realEstateAgent;
}

