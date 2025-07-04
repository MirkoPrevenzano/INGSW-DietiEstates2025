
package com.dietiEstates.backend.dto.response.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstatePreviewInfoDTO
{  
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String street;
    private Double longitude;
    private Double latitude; 
}