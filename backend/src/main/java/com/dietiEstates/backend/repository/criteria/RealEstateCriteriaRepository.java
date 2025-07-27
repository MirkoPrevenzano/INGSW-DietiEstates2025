
package com.dietiEstates.backend.repository.criteria;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDTO;
import com.dietiEstates.backend.dto.response.AgentRecentRealEstateDTO;
import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDTO;
import com.dietiEstates.backend.extra.CoordinatesMinMax;


public interface RealEstateCriteriaRepository 
{
    public Page<RealEstatePreviewInfoDTO> findRealEstatePreviewInfosByFilters(Map<String,String> filters, Pageable page, CoordinatesMinMax coordinatesMinMax);
    public List<AgentRecentRealEstateDTO> findAgentRecentRealEstatesByAgent(Long agentId, Integer limit);
    public List<AgentDashboardRealEstateStatsDTO> findAgentDashboardRealEstateStatsByAgent(Long agentId, Pageable page);
    public Long findLastUploadedByAgent(Long agentId);
}