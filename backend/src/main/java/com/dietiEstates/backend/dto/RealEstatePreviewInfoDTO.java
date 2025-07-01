
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstatePreviewInfoDTO
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
    private String street;
    
    @NonNull
    private Double longitude;
    
    @NonNull
    private Double latitude; 
}