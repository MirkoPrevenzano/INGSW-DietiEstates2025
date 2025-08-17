
package com.dietiEstates.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.response.RealEstateCompleteInfoDto;
import com.dietiEstates.backend.dto.response.RealEstateSearchDto;
import com.dietiEstates.backend.service.RealEstateService;
import com.dietiEstates.backend.service.photo.PhotoResult;
import com.dietiEstates.backend.validator.RealEstateFiltersValidator;
import com.dietiEstates.backend.validator.groups.OnCreate;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/real-estates")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RealEstateController 
{
    private final RealEstateService realEstateService;


    @PostMapping
    public ResponseEntity<Long> createRealEstate(@Validated(value = {OnCreate.class, Default.class}) @RequestBody RealEstateCreationDto realEstateCreationDto, Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(realEstateService.createRealEstate(userDetails.getUsername(), realEstateCreationDto));
                                 
    }

    @PostMapping(value = "/{realEstateId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPhotos(Authentication authentication,
                                              @RequestParam("photos") MultipartFile[] file, 
                                              @PathVariable("realEstateId") Long realEstateId) throws IOException
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        realEstateService.uploadPhotos(userDetails.getUsername(), file, realEstateId);
        return ResponseEntity.ok().build();            
    }

    @GetMapping(value = "/{realEstateId}/photos")
    public ResponseEntity<List<PhotoResult<String>>> getPhotos(@PathVariable("realEstateId") Long realEstateId) throws IOException
    {        
        return ResponseEntity.ok(realEstateService.getPhotos(realEstateId));
    }
   
    @GetMapping
    public ResponseEntity<RealEstateSearchDto> search(@RequestParam("page") Integer page,
                                                   @RequestParam("limit") Integer limit, 
                                                   @Valid @RealEstateFiltersValidator @RequestParam Map<String,String> filters) 
    {
        RealEstateSearchDto realEstateSearchDto = realEstateService.search(filters, PageRequest.of(page, limit));                
        return ResponseEntity.ok(realEstateSearchDto);
    }

    @GetMapping("/{realEstateId}")
    public ResponseEntity<RealEstateCompleteInfoDto> getRealEstateCompleteInfo(@PathVariable("realEstateId") Long realEstateId, Authentication authentication) 
    {
        return ResponseEntity.ok(realEstateService.getRealEstateCompleteInfo(realEstateId, authentication));
    }
}



/* 
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
} */