
package com.dietiEstates.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.dto.RealEstatePreviewsFirstPageDTO;
import com.dietiEstates.backend.extra.CoordinatesMinMax;
import com.dietiEstates.backend.dto.RealEstatePreviewDTO;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.util.FindByRadiusUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateService 
{
    private final RealEstateRepository realEstateRepository;
    private final FindByRadiusUtil findByRadiusUtil;



    public RealEstatePreviewsFirstPageDTO search3(Map<String,String> filters, Pageable page)
    {
        CoordinatesMinMax coordinatesMinMax = findByRadiusUtil.calcoloLatLongMinMax(Integer.valueOf(filters.get("radius")), 
                                                                               Double.valueOf(filters.get("lat")), 
                                                                               Double.valueOf(filters.get("lon")));

        Page<RealEstatePreviewDTO> realEstatePreviewsPage = realEstateRepository.findRealEstateByFilters3(filters, page, coordinatesMinMax);

        RealEstatePreviewsFirstPageDTO RealEstatePreviewsFirstPageDTO = new RealEstatePreviewsFirstPageDTO(realEstatePreviewsPage.getContent(),
                                                                                                        realEstatePreviewsPage.getTotalElements(), 
                                                                                                        realEstatePreviewsPage.getTotalPages());
        return RealEstatePreviewsFirstPageDTO;
    }


    public List<RealEstatePreviewDTO> search4(Map<String,String> filters, Pageable page)
    {
        CoordinatesMinMax coordinatesMinMax = findByRadiusUtil.calcoloLatLongMinMax(Integer.valueOf(filters.get("radius")), 
                                                                               Double.valueOf(filters.get("lat")), 
                                                                               Double.valueOf(filters.get("lon")));
                                                                               
        return realEstateRepository.findRealEstateByFilters4(filters, page, coordinatesMinMax);
    }
}
