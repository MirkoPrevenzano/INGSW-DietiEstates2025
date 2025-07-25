
package com.dietiEstates.backend.dto.response;

import java.util.List;

import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateSearchDTO
{
    private List<RealEstatePreviewInfoDTO> realEstatePreviews;
    private long totalElements;
    private int totalPages;
}