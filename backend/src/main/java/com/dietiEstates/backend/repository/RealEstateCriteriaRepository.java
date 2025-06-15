
package com.dietiEstates.backend.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dietiEstates.backend.dto.response.RealEstatePreviewDTO;
import com.dietiEstates.backend.dto.response.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.response.RealEstateStatsDTO;
import com.dietiEstates.backend.extra.CoordinatesMinMax;



public interface RealEstateCriteriaRepository 
{
    public Page<RealEstatePreviewDTO> findPreviewsByFilters(Map<String,String> filters, Pageable page, CoordinatesMinMax coordinatesMinMax);
    public List<RealEstateRecentDTO> findRecentsByAgent(Long agentId, Integer limit);
    public List<RealEstateStatsDTO> findStatsByAgent(Long agentId, Pageable page);
    public Long findLastUploadedByAgent(Long agentId);
}