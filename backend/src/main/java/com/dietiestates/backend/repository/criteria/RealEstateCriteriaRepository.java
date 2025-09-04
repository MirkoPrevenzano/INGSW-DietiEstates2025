
package com.dietiestates.backend.repository.criteria;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dietiestates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiestates.backend.dto.response.AgentRecentRealEstateDto;
import com.dietiestates.backend.dto.response.support.RealEstatePreviewInfoDto;
import com.dietiestates.backend.extra.CoordinatesBoundingBox;


public interface RealEstateCriteriaRepository 
{
    public Page<RealEstatePreviewInfoDto> findRealEstatePreviewInfosByFilters(Map<String,String> filters, Pageable page, CoordinatesBoundingBox coordinatesBoundingBox);

    public List<AgentRecentRealEstateDto> findAgentRecentRealEstatesByAgentId(Long agentId, Integer limit);

    public List<AgentDashboardRealEstateStatsDto> findAgentDashboardRealEstateStatsByAgentId(Long agentId, Pageable page);
    
    public Long findLastUploadedByAgentId(Long agentId);
}