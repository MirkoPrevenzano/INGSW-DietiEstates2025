
package com.dietiestates.backend.dto.response;

import java.util.List;

import com.dietiestates.backend.dto.response.support.RealEstatePreviewInfoDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateSearchDto
{
    private List<RealEstatePreviewInfoDto> realEstatePreviewInfoDtoList;
    private int totalElementsOfPage;
    private long totalElements;
    private int totalPages;
}