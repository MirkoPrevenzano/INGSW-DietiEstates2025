
package com.dietiEstates.backend.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstatePreviewsFirstPageDTO
{
    @NonNull
    private List<RealEstatePreviewDTO> realEstatePreviews;

    @NonNull
    private Long totalElements;

    @NonNull
    private Integer totalPages;
}