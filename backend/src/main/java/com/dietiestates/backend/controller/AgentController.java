
package com.dietiestates.backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiestates.backend.dto.request.AgentCreationDto;
import com.dietiestates.backend.dto.response.AgentDashboardPersonalStatsDto;
import com.dietiestates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiestates.backend.dto.response.AgentPublicInfoDto;
import com.dietiestates.backend.dto.response.AgentRecentRealEstateDto;
import com.dietiestates.backend.service.AgentService;
import com.dietiestates.backend.service.export.ExportingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agents")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AgentController 
{
    private final AgentService agentService;


    @PostMapping
    public ResponseEntity<Void> createAgent(@Valid @RequestBody AgentCreationDto agentCreationDto, Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        agentService.createAgent(userDetails.getUsername(), agentCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
   
    @GetMapping(path = "/public-info")
    public ResponseEntity<AgentPublicInfoDto> getAgentPublicInfo(Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.OK).body(agentService.getAgentPublicInfo(userDetails.getUsername()));
    }

    @GetMapping(path = "/recent-real-estates/{limit}")
    public ResponseEntity<List<AgentRecentRealEstateDto>> getAgentRecentRealEstates(@PathVariable("limit") Integer limit, Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<AgentRecentRealEstateDto> agentRecentRealEstateDtos = agentService.getAgentRecentRealEstates(userDetails.getUsername(), limit);
        return ResponseEntity.status(HttpStatus.OK).body(agentRecentRealEstateDtos);
    }

    @GetMapping(value = "/dashboard/csv-report")
    public ResponseEntity<byte[]> exportCsvReport(Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ExportingResult exportingResult = agentService.exportCsvReport(userDetails.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(exportingResult.getContentType());
        headers.setContentDisposition(ContentDisposition.attachment().filename(exportingResult.getFilename()).build());

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(exportingResult.getExportingBytes());
    }

    @GetMapping(value = "/dashboard/pdf-report")
    public ResponseEntity<byte[]> exportPdfReport(Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ExportingResult exportingResult = agentService.exportPdfReport(userDetails.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(exportingResult.getContentType());
        headers.setContentDisposition(ContentDisposition.attachment().filename(exportingResult.getFilename()).build());

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(exportingResult.getExportingBytes());
    }

    @GetMapping(value = "/dashboard/personal-stats")
    public ResponseEntity<AgentDashboardPersonalStatsDto> getAgentDashboardPersonalStats(Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        AgentDashboardPersonalStatsDto agentDashboardPersonalStatsDto = agentService.getAgentDashboardPersonalStats(userDetails.getUsername());
        return ResponseEntity.ok().body(agentDashboardPersonalStatsDto);
    }

    @GetMapping(value = "/dashboard/real-estate-stats/{page}/{limit}")
    public ResponseEntity<List<AgentDashboardRealEstateStatsDto>> getAgentDashboardRealEstateStats(Authentication authentication, 
                                                   @PathVariable("page") Integer page,
                                                   @PathVariable("limit") Integer limit) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<AgentDashboardRealEstateStatsDto> realEstateStatsDTOs = agentService.getAgentDashboardRealEstateStats(userDetails.getUsername(), PageRequest.of(page, limit));
        return ResponseEntity.ok().body(realEstateStatsDTOs);
    }
}