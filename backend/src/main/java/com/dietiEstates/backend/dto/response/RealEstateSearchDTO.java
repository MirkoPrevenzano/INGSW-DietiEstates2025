
package com.dietiEstates.backend.dto.response;

import java.util.List;

import com.dietiEstates.backend.dto.RealEstatePreviewInfoDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateSearchDTO
{
    @NonNull
    private List<RealEstatePreviewInfoDTO> realEstatePreviews;

    @NonNull
    private Long totalElements;

    @NonNull
    private Integer totalPages;
}