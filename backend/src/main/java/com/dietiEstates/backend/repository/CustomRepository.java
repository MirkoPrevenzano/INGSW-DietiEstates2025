
package com.dietiEstates.backend.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dietiEstates.backend.dto.LatLongMinMax;
import com.dietiEstates.backend.dto.RealEstatePreviewDTO;
import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.RealEstateStatsDTO;
import java.util.Optional;
import com.dietiEstates.backend.model.Customer;

public interface CustomRepository 
{
    Page<RealEstatePreviewDTO> findRealEstateByFilters(Map<String,String> filters, Pageable page);
    List<RealEstatePreviewDTO> findRealEstateByFilters2(Map<String,String> filters, Pageable page);
    Page<RealEstatePreviewDTO> findRealEstateByFilters3(Map<String,String> filters, Pageable page, LatLongMinMax latLongMinMax);
    List<RealEstatePreviewDTO> findRealEstateByFilters4(Map<String,String> filters, Pageable page, LatLongMinMax latLongMinMax);
    List<RealEstateRecentDTO> findRecentRealEstates(Long agentId, Integer limit);
    List<RealEstateStatsDTO> findRealEstateStats2(Long agentId, Pageable page);
    Long findLastRealEstate(Long agentId);
}
