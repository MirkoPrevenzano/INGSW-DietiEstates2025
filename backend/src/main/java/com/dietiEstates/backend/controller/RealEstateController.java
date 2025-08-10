
package com.dietiEstates.backend.controller;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.response.RealEstateCompleteInfoDto;
import com.dietiEstates.backend.dto.response.RealEstateSearchDto;
import com.dietiEstates.backend.service.RealEstateService;
import com.dietiEstates.backend.validator.RealEstateFiltersValidator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/real-estate")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RealEstateController 
{
    private final RealEstateService realEstateService;

    
    @GetMapping(path = "search3")
    public ResponseEntity<RealEstateSearchDto> search(@RequestParam("page") Integer page,
                                                   @RequestParam("limit") Integer limit, 
                                                   @Valid @RealEstateFiltersValidator @RequestParam Map<String,String> filters) 
    {
        RealEstateSearchDto realEstateSearchDto = realEstateService.search(filters, PageRequest.of(page, limit));                
        return ResponseEntity.ok(realEstateSearchDto);
    }


    @GetMapping("/view/{realEstateId}")
    public ResponseEntity<RealEstateCompleteInfoDto> getRealEstateCompleteInfo(@PathVariable("realEstateId") Long realEstateId, Authentication authentication) 
    {
        return ResponseEntity.ok(realEstateService.getRealEstateCompleteInfo(realEstateId, authentication));
    }
}