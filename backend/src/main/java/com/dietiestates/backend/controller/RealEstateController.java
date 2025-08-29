
package com.dietiestates.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.dto.response.RealEstateCompleteInfoDto;
import com.dietiestates.backend.dto.response.RealEstateSearchDto;
import com.dietiestates.backend.service.RealEstateService;
import com.dietiestates.backend.service.photo.PhotoResult;
import com.dietiestates.backend.validator.RealEstateFiltersValidator;
import com.dietiestates.backend.validator.groups.OnCreate;

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
    @Operation(description = "",
               tags = "Real Estates")
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Collaboratore creato!",
                                ref = ""),
                   @ApiResponse(responseCode = "500",
                                description = "Errore interno non gestito",
                                ref = "")})
    public ResponseEntity<Long> createRealEstate(@Validated(value = {OnCreate.class, Default.class}) @RequestBody RealEstateCreationDto realEstateCreationDto, Authentication authentication) 
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(realEstateService.createRealEstate(userDetails.getUsername(), realEstateCreationDto));
                                 
    }

    @PostMapping(value = "/{realEstateId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "", 
               description = "",
               tags = "Admins")
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Collaboratore creato!",
                                ref = ""),
                   @ApiResponse(responseCode = "500",
                                description = "Errore interno non gestito",
                                ref = "")})
    public ResponseEntity<Void> uploadPhotos(Authentication authentication,
                                              @RequestParam("photos") MultipartFile[] file, 
                                              @PathVariable("realEstateId") Long realEstateId) throws IOException
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        realEstateService.uploadPhotos(userDetails.getUsername(), file, realEstateId);
        return ResponseEntity.ok().build();            
    }

    @GetMapping(value = "/{realEstateId}/photos")
    @Operation(summary = "", 
               description = "",
               tags = "Admins")
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Collaboratore creato!",
                                ref = ""),
                   @ApiResponse(responseCode = "500",
                                description = "Errore interno non gestito",
                                ref = "")})
    public ResponseEntity<List<PhotoResult<String>>> getPhotos(@PathVariable("realEstateId") Long realEstateId)
    {        
        return ResponseEntity.ok(realEstateService.getPhotos(realEstateId));
    }
   
    @GetMapping
    @Operation(summary = "", 
               description = "",
               tags = "Admins")
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Collaboratore creato!",
                                ref = ""),
                   @ApiResponse(responseCode = "500",
                                description = "Errore interno non gestito",
                                ref = "")})
    public ResponseEntity<RealEstateSearchDto> search(@RequestParam("page") Integer page,
                                                   @RequestParam("limit") Integer limit, 
                                                   @Valid @RealEstateFiltersValidator @RequestParam Map<String,String> filters) 
    {
        RealEstateSearchDto realEstateSearchDto = realEstateService.search(filters, PageRequest.of(page, limit));                
        return ResponseEntity.ok(realEstateSearchDto);
    }

    @GetMapping("/{realEstateId}")
    @Operation(summary = "", 
               description = "",
               tags = "Admins")
    @ApiResponses({@ApiResponse(responseCode = "201",
                                description = "Collaboratore creato!",
                                ref = ""),
                   @ApiResponse(responseCode = "500",
                                description = "Errore interno non gestito",
                                ref = "")})
    public ResponseEntity<RealEstateCompleteInfoDto> getRealEstateCompleteInfo(@PathVariable("realEstateId") Long realEstateId, Authentication authentication) 
    {
        return ResponseEntity.ok(realEstateService.getRealEstateCompleteInfo(realEstateId, authentication));
    }
}