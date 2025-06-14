
package com.dietiEstates.backend.controller;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.response.RealEstatePreviewDTO;
import com.dietiEstates.backend.dto.response.RealEstatePreviewsFirstPageDTO;
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
    public ResponseEntity<RealEstatePreviewsFirstPageDTO> aaa(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam Map<String,String> filters) 
    {
        RealEstatePreviewsFirstPageDTO realEstatePreviewsFirstPageDTO = realEstateService.search3(filters, PageRequest.of(page, limit));

        for(RealEstatePreviewDTO realEstate : realEstatePreviewsFirstPageDTO.getRealEstatePreviews())
            log.info(realEstate.getLatitude().toString() + " : " + realEstate.getLongitude().toString()); 
        
        log.info(realEstatePreviewsFirstPageDTO.getTotalElements().toString() + " : " + realEstatePreviewsFirstPageDTO.getTotalPages().toString());
        
        return ResponseEntity.ok(realEstatePreviewsFirstPageDTO);
    }


    @GetMapping(path = "search4")
    public ResponseEntity<List<RealEstatePreviewDTO>> aaaa(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam Map<String,String> filters) 
    {
        List<RealEstatePreviewDTO> realEstates = realEstateService.search4(filters, PageRequest.of(page, limit));

        for(RealEstatePreviewDTO realEstate : realEstates)
            log.info(realEstate.getLatitude().toString() + " : " + realEstate.getLongitude().toString()); 
        
        return ResponseEntity.ok(realEstates);
    }

}