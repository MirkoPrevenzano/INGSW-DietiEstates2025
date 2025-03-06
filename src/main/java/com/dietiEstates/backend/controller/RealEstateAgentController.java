
package com.dietiEstates.backend.controller;

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

import com.dietiEstates.backend.dto.RealEstateAgentStatsDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.dto.RealEstateRecentDTO;
import com.dietiEstates.backend.dto.RealEstateStatsDTO;
import com.dietiEstates.backend.model.embeddable.RealEstateAgentStats;
import com.dietiEstates.backend.service.RealEstateAgentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agent")
@RequiredArgsConstructor
@Slf4j
public class RealEstateAgentController 
{
    private final RealEstateAgentService realEstateAgentService;



    @PostMapping(path = "{username}/create-real-estate-for-sale")
    public ResponseEntity<?> createRealEstateForSale(@PathVariable String username, @RequestBody RealEstateForSaleCreationDTO realEstateForSaleCreationDTO) 
    {
        try 
        {
            realEstateAgentService.createRealEstateForSale(username, realEstateForSaleCreationDTO);
            return ResponseEntity.status(HttpStatus.CREATED.value()).build();
        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }
    }


    @PostMapping(path = "{username}/create-real-estate-for-rent")
    public ResponseEntity<?> createRealEstateForRent(@PathVariable String username, @RequestBody RealEstateForRentCreationDTO realEstateForRentCreationDTO) 
    {
        try 
        {
            realEstateAgentService.createRealEstateForRent(username, realEstateForRentCreationDTO);
            return ResponseEntity.status(HttpStatus.CREATED.value()).build();
        } 
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }
    }


    @GetMapping(path = "{username}/recent-real-estates/{limit}")
    public ResponseEntity<List<RealEstateRecentDTO>> aa(@PathVariable("username") String username, @PathVariable("limit") Integer limit) 
    {
        List<RealEstateRecentDTO> realEstates = realEstateAgentService.findRecentRealEstates(username, limit);

        for(RealEstateRecentDTO recentRealEstateDTO : realEstates)
            log.info(recentRealEstateDTO.toString());

        return ResponseEntity.ok(realEstates);
    }


    @GetMapping(path = "{username}/general-stats")
    public ResponseEntity<RealEstateAgentStatsDTO> aaaaaaa(@PathVariable("username") String username) 
    {
        RealEstateAgentStatsDTO realEstateAgentStatsDTO = realEstateAgentService.getAgentStats(username);

        return ResponseEntity.ok().body(realEstateAgentStatsDTO);
    }


    @GetMapping(path = "{username}/estates-stats/{page}/{limit}")
    public ResponseEntity<List<RealEstateStatsDTO>> aaaa(@PathVariable("username") String username, 
                                                   @PathVariable("page") Integer page,
                                                   @PathVariable("limit") Integer limit) 
    {
        List<RealEstateStatsDTO> realEstateStatsDTOs = realEstateAgentService.getRealEstateStats(username, 
                                                                                                 PageRequest.of(page, limit));
        return ResponseEntity.ok().body(realEstateStatsDTOs);
    }

    @GetMapping(path = "{username}/bar-chart-stats")
    public ResponseEntity<Integer[]> aaaa(@PathVariable("username") String username) 
    {
        return ResponseEntity.ok().body(realEstateAgentService.getBarChartStats());
    }
}