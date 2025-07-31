
package com.dietiEstates.backend.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.groups.Default;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDTO;
import com.dietiEstates.backend.dto.response.AgentDashboardPersonalStatsDTO;
import com.dietiEstates.backend.dto.response.AgentRecentRealEstateDTO;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.service.export.ExportReportWrapper;
import com.dietiEstates.backend.service.export.csv.CsvExportService;
import com.dietiEstates.backend.service.export.pdf.PdfExportService;
import com.dietiEstates.backend.validator.groups.OnCreate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agent")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AgentController 
{
    private final AgentService agentService;
    private final PdfExportService pdfExportService;
    private final CsvExportService csvExportService;

    @GetMapping("prova/{param}")
    public String getMethodName(@PathVariable String paòram) 
    {
        return new String();
    }
    

    @PostMapping(path = "{username}/create-real-estate-for-sale/{lol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createRealEstateForSale(@PathVariable() String username, @Validated(value = {OnCreate.class, Default.class}) @RequestBody RealEstateForSaleCreationDTO realEstateForSaleCreationDTO, 
    @Min(5) @PathVariable(required = true) Integer lol, @Min(2) @RequestParam(required = true) Integer prova) 
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
    public ResponseEntity<List<AgentRecentRealEstateDTO>> aa(@PathVariable("username") String username, @PathVariable("limit") Integer limit) 
    {
        List<AgentRecentRealEstateDTO> realEstates = agentService.findRecentRealEstates(username, limit);

        for(AgentRecentRealEstateDTO recentRealEstateDTO : realEstates)
            log.info(recentRealEstateDTO.toString());

        return ResponseEntity.ok(realEstates);
    }


/*     @GetMapping(value = "/{username}/exportCSV2")
    public ResponseEntity<Void> exportToCSV2(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {   
        csvExportService.exportCsvReport(username, response);
        return ResponseEntity.status(HttpStatus.OK).build();
    } */
       

/*     @GetMapping(value = "/{username}/exportPDF2")
    public ResponseEntity<Void> exportToPDF2(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {
        pdfExportService.exportPdfReport(username, response);
        return ResponseEntity.status(HttpStatus.OK).build();
    } */

    @GetMapping(value = "/{username}/exportCSV2")
    public ResponseEntity<byte[]> exportToCSV2(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {
        ExportReportWrapper exportReportWrapper = csvExportService.exportCsvReport(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(exportReportWrapper.getContentType()));
        headers.setContentDisposition(ContentDisposition.attachment().filename(exportReportWrapper.getFilename()).build());

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(exportReportWrapper.getData());
    }


    @GetMapping(value = "/{username}/exportPDF2")
    public ResponseEntity<byte[]> exportToPDF2(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {
        ExportReportWrapper exportReportWrapper = pdfExportService.exportPdfReport(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(exportReportWrapper.getContentType()));
        headers.setContentDisposition(ContentDisposition.attachment().filename(exportReportWrapper.getFilename()).build());

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(exportReportWrapper.getData());
    }


    @GetMapping(path = "{username}/general-stats")
    public ResponseEntity<AgentDashboardPersonalStatsDTO> aaaaaaa(@PathVariable("username") String username) 
    {
        AgentDashboardPersonalStatsDTO agentDashboardPersonalStatsDTO = agentService.getAgentDashboardStats(username);
        return ResponseEntity.ok().body(agentDashboardPersonalStatsDTO);
    }


    @GetMapping(path = "{username}/estates-stats/{page}/{limit}")
    public ResponseEntity<List<AgentDashboardRealEstateStatsDTO>> aaaa(@PathVariable("username") String username, 
                                                   @PathVariable("page") Integer page,
                                                   @PathVariable("limit") Integer limit) 
    {
        List<AgentDashboardRealEstateStatsDTO> realEstateStatsDTOs = agentService.getRealEstateStats(username,PageRequest.of(page, limit));
        
        return ResponseEntity.ok().body(realEstateStatsDTOs);
    }

    
    @GetMapping(path = "{username}/bar-chart-stats")
    public ResponseEntity<Integer[]> aaaa(@PathVariable("username") String username) 
    {
        return ResponseEntity.ok().body(agentService.getBarChartStats());
    }
}