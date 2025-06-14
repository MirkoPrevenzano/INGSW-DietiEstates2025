
package com.dietiEstates.backend.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.AgentStatsDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.RealEstateStatsDTO;
import com.dietiEstates.backend.model.embeddable.AgentStats;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.util.CsvUtil;
import com.dietiEstates.backend.util.PdfUtil;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agent")
@RequiredArgsConstructor
@Slf4j
public class AgentController 
{
    private final AgentService agentService;
    private final CsvUtil csvUtil;
    private final PdfUtil pdfUtil;



    @PostMapping(path = "{username}/create-real-estate-for-sale")
    public ResponseEntity<Long> createRealEstateForSale(@PathVariable String username, @RequestBody RealEstateForSaleCreationDTO realEstateForSaleCreationDTO) 
    {
        try 
        {
            return ResponseEntity.status(HttpStatus.CREATED.value())
                                 .body(agentService.createRealEstateForSale(username, realEstateForSaleCreationDTO));
                                 
        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }
    }


    @PostMapping(path = "{username}/create-real-estate-for-rent")
    public ResponseEntity<Long> createRealEstateForRent(@PathVariable String username, @RequestBody RealEstateForRentCreationDTO realEstateForRentCreationDTO) 
    {
        try 
        {
            return ResponseEntity.status(HttpStatus.CREATED.value())
                                 .body(agentService.createRealEstateForRent(username, realEstateForRentCreationDTO));

        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }
    }


    @GetMapping(path = "{username}/recent-real-estates/{limit}")
    public ResponseEntity<List<RealEstateRecentDTO>> aa(@PathVariable("username") String username, @PathVariable("limit") Integer limit) 
    {
        List<RealEstateRecentDTO> realEstates = agentService.findRecentRealEstates(username, limit);

        for(RealEstateRecentDTO recentRealEstateDTO : realEstates)
            log.info(recentRealEstateDTO.toString());

        return ResponseEntity.ok(realEstates);
    }


    @GetMapping(value = "/{username}/exportCSV")
    public void exportToCSV(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {
        csvUtil.writeCsvResponse(username,response);
    }
       
    
    //Il controller bisogna che chiama solo il metodo del service
    @GetMapping("{username}/exportPDF")
    public void exportToPDF(@PathVariable("username") String username, HttpServletResponse response) throws DocumentException, IOException 
    {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+username + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue); 

        try 
        {  
            pdfUtil.writePdfResponse(username, response);
        } 
        catch (DocumentException | IOException e) 
        {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setHeader("Error", "Errore durante l'esportazione del PDF!"); 
        }
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
        List<RealEstateStatsDTO> realEstateStatsDTOs = agentService.getRealEstateStats(username, 
                                                                                                 PageRequest.of(page, limit));
        return ResponseEntity.ok().body(realEstateStatsDTOs);
    }

    @GetMapping(path = "{username}/bar-chart-stats")
    public ResponseEntity<Integer[]> aaaa(@PathVariable("username") String username) 
    {
        return ResponseEntity.ok().body(agentService.getBarChartStats());
    }
}