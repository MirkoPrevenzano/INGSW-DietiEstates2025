
package com.dietiEstates.backend.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dietiEstates.backend.dto.LatLongMinMax;
import com.dietiEstates.backend.dto.RealEstatePreviewDTO;
import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.RealEstateStatsDTO;



public interface RealEstateCustomRepository 
{
    public Page<RealEstatePreviewDTO> findRealEstateByFilters3(Map<String,String> filters, Pageable page, LatLongMinMax latLongMinMax);
    public List<RealEstatePreviewDTO> findRealEstateByFilters4(Map<String,String> filters, Pageable page, LatLongMinMax latLongMinMax);
    public List<RealEstateRecentDTO> findRecentRealEstates(Long agentId, Integer limit);
    public List<RealEstateStatsDTO> findRealEstateStats2(Long agentId, Pageable page);
    public Long findLastRealEstate(Long agentId);
}
