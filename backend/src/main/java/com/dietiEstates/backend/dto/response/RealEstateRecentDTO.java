
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateRecentDTO 
{
    private Long id;
    private String title;
    private String description;
    
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd', 'HH:mm:ss")
    private LocalDateTime uploadingDate;
}