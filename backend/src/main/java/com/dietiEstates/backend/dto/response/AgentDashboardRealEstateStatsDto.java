
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDashboardRealEstateStatsDto 
{
    private long id;

    private String title;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss")
    private LocalDateTime uploadingDate;

    private long viewsNumber;

    private long visitsNumber;
    
    private long offersNumber;
}