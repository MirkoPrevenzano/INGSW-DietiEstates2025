
package com.dietiEstates.backend.controller;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.response.RealEstateCompleteInfoDTO;
import com.dietiEstates.backend.dto.response.RealEstateSearchDTO;
import com.dietiEstates.backend.dto.response.support.RealEstatePreviewInfoDTO;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.service.RealEstateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//mettere nel customer controller (idea)
@RestController
@RequestMapping(path = "/real-estate")
@RequiredArgsConstructor
@Slf4j
public class RealEstateController 
{
    private final RealEstateService realEstateService;

    

    @GetMapping(path = "search3")
    public ResponseEntity<RealEstateSearchDTO> aaa(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam Map<String,String> filters) 
    {
        RealEstateSearchDTO realEstateSearchDTO = realEstateService.search3(filters, PageRequest.of(page, limit));

        for(RealEstatePreviewInfoDTO realEstate : realEstateSearchDTO.getRealEstatePreviews())
            log.info(realEstate.getLatitude().toString() + " : " + realEstate.getLongitude().toString()); 
        
        log.info(realEstateSearchDTO.getTotalElements().toString() + " : " + realEstateSearchDTO.getTotalPages().toString());
        
        return ResponseEntity.ok(realEstateSearchDTO);
    }


    @GetMapping("/view/{realEstateId}")
    public ResponseEntity<RealEstateCompleteInfoDTO> viewRealEstate(@PathVariable("realEstateId") Long realEstateId, Authentication authentication) 
    {
        return ResponseEntity.ok(realEstateService.getRealEstateCompleteInfo(realEstateId, authentication));
    }
}