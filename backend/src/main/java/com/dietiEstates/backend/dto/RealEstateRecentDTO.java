
package com.dietiEstates.backend.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateRecentDTO 
{
    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String description;
    
    @NonNull
    private LocalDateTime uploadingDate;
}