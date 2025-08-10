
package com.dietiEstates.backend.dto.response.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstatePreviewInfoDTO
{  
    private long id;

    private String title;

    private String description;

    private double price;

    private String street;

    private double longitude;
    
    private double latitude; 
}