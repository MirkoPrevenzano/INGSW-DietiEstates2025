
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateStatsDTO 
{
    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private LocalDateTime uploadingDate;

    @NonNull
    private Long viewsNumber;

    @NonNull
    private Long visitsNumber;

    @NonNull
    private Long offersNumber;
}