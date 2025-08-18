

package com.dietiEstates.backend.controller;

import java.io.IOException;
import java.util.List;

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
import com.dietiEstates.backend.dto.request.AgentCreationDto;
import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiEstates.backend.dto.response.AgentPublicInfoDto;
import com.dietiEstates.backend.dto.response.AgentDashboardPersonalStatsDto;
import com.dietiEstates.backend.dto.response.AgentRecentRealEstateDto;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.service.export.ExportingResult;
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
    public ResponseEntity<Void> createAgent(@RequestBody AgentCreationDto agentCreationDto, Authentication authentication) 
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
    public ResponseEntity<byte[]> exportCsvReport(Authentication authentication) throws IOException 
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
    public ResponseEntity<byte[]> exportPdfReport(Authentication authentication) throws IOException 
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

    @GetMapping(value = "/dashboard/real-estate-stats/{limit}")
    public ResponseEntity<List<AgentDashboardRealEstateStatsDto>> getAgentDashboardRealEstateStats(Authentication authentication, 
                                                   @PathVariable("page") Integer page,
                                                   @PathVariable("limit") Integer limit) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<AgentDashboardRealEstateStatsDto> realEstateStatsDTOs = agentService.getAgentDashboardRealEstateStats(userDetails.getUsername(), PageRequest.of(page, limit));
        return ResponseEntity.ok().body(realEstateStatsDTOs);
    }
}


/* 
package com.dietiEstates.backend.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.groups.Default;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.dto.request.AgentCreationDto;
import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDto;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDto;
import com.dietiEstates.backend.dto.request.UpdatePasswordDto;
import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiEstates.backend.dto.response.AgentDashboardPersonalStatsDto;
import com.dietiEstates.backend.dto.response.AgentRecentRealEstateDto;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.service.export.ExportingResult;
import com.dietiEstates.backend.service.export.csv.CsvExportService;
import com.dietiEstates.backend.service.export.pdf.PdfExportService;
import com.dietiEstates.backend.service.photo.PhotoResult;
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


    @PostMapping(path = "{username}/create-real-estate")
    public ResponseEntity<Long> createRealEstate(@PathVariable() String username, @Validated(value = {OnCreate.class, Default.class}) @RequestBody RealEstateCreationDto realEstateCreationDto) 
    {
        if (realEstateCreationDto instanceof RealEstateForSaleCreationDto) 
        {
            log.info("\n\ninstanza di for sale");
        }
        else if (realEstateCreationDto instanceof RealEstateCreationDto)
            log.info("\n\ninstanza di for creation\n\n");

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(agentService.createRealEstate(username, realEstateCreationDto));
                                 
    }


    @PostMapping(path = "{username}/create-real-estate-for-sale")
    public ResponseEntity<Long> createRealEstateForSale(@PathVariable() String username, @Validated(value = {OnCreate.class, Default.class}) @RequestBody RealEstateForSaleCreationDto realEstateForSaleCreationDto) 
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(agentService.createRealEstate(username, realEstateForSaleCreationDto));
                                 
    }


    @PostMapping(path = "{username}/create-real-estate-for-rent")
    public ResponseEntity<Long> createRealEstateForRent(@PathVariable String username, @Validated(OnCreate.class) @RequestBody RealEstateForRentCreationDto realEstateForRentCreationDto) 
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(agentService.createRealEstate(username, realEstateForRentCreationDto));
    }



    @PostMapping(value = "{username}/upload-photo2/{realEstateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPhoto2(@PathVariable("username") String username, 
                                              @RequestParam("photos") MultipartFile[] file, 
                                              @PathVariable("realEstateId") Long realEstateId) throws IOException
    {
        agentService.uploadPhoto2(username, file, realEstateId);
        return ResponseEntity.ok().build();            
    }


    @GetMapping(value = "get-photos2/{realEstateId}")
    public ResponseEntity<List<PhotoResult<String>>> getPhoto2(@PathVariable("realEstateId") Long realEstateId) throws IOException
    {        
        return ResponseEntity.ok(agentService.getPhoto2(realEstateId));
    }
   
   
    @GetMapping(path = "{username}/recent-real-estates/{limit}")
    public ResponseEntity<List<AgentRecentRealEstateDto>> aa(@PathVariable("username") String username, @PathVariable("limit") Integer limit) 
    {
        List<AgentRecentRealEstateDto> realEstates = agentService.getAgentRecentRealEstates(username, limit);

        for(AgentRecentRealEstateDto recentRealEstateDTO : realEstates)
            log.info(recentRealEstateDTO.toString());

        return ResponseEntity.ok(realEstates);
    }

    @GetMapping(value = "/{username}/exportCSV2")
    public ResponseEntity<byte[]> exportToCSV(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {
        ExportingResult exportingResult = agentService.exportCsvReport(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(exportingResult.getContentType());
        headers.setContentDisposition(ContentDisposition.attachment().filename(exportingResult.getFilename()).build());

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(exportingResult.getExportingBytes());
    }


    @GetMapping(value = "/{username}/exportPDF2")
    public ResponseEntity<byte[]> exportToPDF(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {
        ExportingResult exportingResult = agentService.exportPdfReport(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(exportingResult.getContentType());
        headers.setContentDisposition(ContentDisposition.attachment().filename(exportingResult.getFilename()).build());

        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .body(exportingResult.getExportingBytes());
    }


    @GetMapping(path = "{username}/general-stats")
    public ResponseEntity<AgentDashboardPersonalStatsDto> aaaaaaa(@PathVariable("username") String username) 
    {
        AgentDashboardPersonalStatsDto agentDashboardPersonalStatsDto = agentService.getAgentDashboardPersonalStats(username);
        return ResponseEntity.ok().body(agentDashboardPersonalStatsDto);
    }


    @GetMapping(path = "{username}/estates-stats/{page}/{limit}")
    public ResponseEntity<List<AgentDashboardRealEstateStatsDto>> aaaa(@PathVariable("username") String username, 
                                                   @PathVariable("page") Integer page,
                                                   @PathVariable("limit") Integer limit) 
    {
        List<AgentDashboardRealEstateStatsDto> realEstateStatsDTOs = agentService.getAgentDashboardRealEstateStats(username,PageRequest.of(page, limit));
        
        return ResponseEntity.ok().body(realEstateStatsDTOs);
    }
} */