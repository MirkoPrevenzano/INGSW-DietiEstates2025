
package com.dietiEstates.backend.repository.criteria;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiEstates.backend.dto.response.AgentRecentRealEstateDto;
import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDto;
import com.dietiEstates.backend.extra.CoordinatesBoundingBox;


public interface RealEstateCriteriaRepository 
{
    public Page<RealEstatePreviewInfoDto> findRealEstatePreviewInfosByFilters(Map<String,String> filters, Pageable page, CoordinatesBoundingBox coordinatesBoundingBox);
    public List<AgentRecentRealEstateDto> findAgentRecentRealEstatesByAgent(Long agentId, Integer limit);
    public List<AgentDashboardRealEstateStatsDto> findAgentDashboardRealEstateStatsByAgent(Long agentId, Pageable page);
    public Long findLastUploadedByAgent(Long agentId);
}