
package com.dietiEstates.backend.controller;


import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.response.AgentStatsDTO;
import com.dietiEstates.backend.dto.response.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.response.RealEstateStatsDTO;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.service.export.csv.CsvExportService;
import com.dietiEstates.backend.service.export.pdf.PdfExportService;
import com.dietiEstates.backend.validator.groups.OnCreate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agent")
@RequiredArgsConstructor
@Slf4j
public class AgentController 
{
    private final AgentService agentService;
    private final PdfExportService pdfExportService;
    private final CsvExportService csvExportService;


    @PostMapping(path = "{username}/create-real-estate-for-sale")
    public ResponseEntity<Long> createRealEstateForSale(@PathVariable String username, @Validated(OnCreate.class) @RequestBody RealEstateForSaleCreationDTO realEstateForSaleCreationDTO) 
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(agentService.createRealEstate(username, realEstateForSaleCreationDTO));
                                 
    }


    @PostMapping(path = "{username}/create-real-estate-for-rent")
    public ResponseEntity<Long> createRealEstateForRent(@PathVariable String username, @Validated(OnCreate.class) @RequestBody RealEstateForRentCreationDTO realEstateForRentCreationDTO) 
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(agentService.createRealEstate(username, realEstateForRentCreationDTO));
    }


    @GetMapping(path = "{username}/recent-real-estates/{limit}")
    public ResponseEntity<List<RealEstateRecentDTO>> aa(@PathVariable("username") String username, @PathVariable("limit") Integer limit) 
    {
        List<RealEstateRecentDTO> realEstates = agentService.findRecentRealEstates(username, limit);

        for(RealEstateRecentDTO recentRealEstateDTO : realEstates)
            log.info(recentRealEstateDTO.toString());

        return ResponseEntity.ok(realEstates);
    }


    @GetMapping(value = "/{username}/exportCSV2")
    public ResponseEntity<Void> exportToCSV2(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {   
        csvExportService.exportCsvReport(username, response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
       

    @GetMapping(value = "/{username}/exportPDF2")
    public ResponseEntity<Void> exportToPDF2(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {
        pdfExportService.exportPdfReport(username, response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping(path = "{username}/general-stats")
    public ResponseEntity<AgentStatsDTO> aaaaaaa(@PathVariable("username") String username) 
    {
        AgentStatsDTO agentStatsDTO = agentService.getAgentStats(username);
        return ResponseEntity.ok().body(agentStatsDTO);
    }


    @GetMapping(path = "{username}/estates-stats/{page}/{limit}")
    public ResponseEntity<List<RealEstateStatsDTO>> aaaa(@PathVariable("username") String username, 
                                                   @PathVariable("page") Integer page,
                                                   @PathVariable("limit") Integer limit) 
    {
        List<RealEstateStatsDTO> realEstateStatsDTOs = agentService.getRealEstateStats(username,PageRequest.of(page, limit));
        
        return ResponseEntity.ok().body(realEstateStatsDTOs);
    }

    
    @GetMapping(path = "{username}/bar-chart-stats")
    public ResponseEntity<Integer[]> aaaa(@PathVariable("username") String username) 
    {
        return ResponseEntity.ok().body(agentService.getBarChartStats());
    }
}