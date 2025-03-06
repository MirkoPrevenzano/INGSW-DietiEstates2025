
package com.dietiEstates.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.dto.RealEstatePreviewsFirstPageDTO;
import com.dietiEstates.backend.dto.RealEstatePreviewDTO;
import com.dietiEstates.backend.model.RealEstate;
import com.dietiEstates.backend.repository.RealEstateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class RealEstateService 
{
    private final RealEstateRepository realEstateRepository;


    
    public RealEstatePreviewsFirstPageDTO search(Map<String,String> filters, Pageable page)
    {
        Page<RealEstatePreviewDTO> realEstatePreviewsPage = realEstateRepository.findRealEstateByFilters(filters, page);

        RealEstatePreviewsFirstPageDTO realEstateFiltersFirstPageDTO = new RealEstatePreviewsFirstPageDTO(realEstatePreviewsPage.getContent(),
                                                                                                        realEstatePreviewsPage.getTotalElements(), 
                                                                                                        realEstatePreviewsPage.getTotalPages());
        return realEstateFiltersFirstPageDTO;
    }
    

    public List<RealEstatePreviewDTO> search2(Map<String,String> filters, Pageable page)
    {
        return realEstateRepository.findRealEstateByFilters2(filters, page);
    }
}