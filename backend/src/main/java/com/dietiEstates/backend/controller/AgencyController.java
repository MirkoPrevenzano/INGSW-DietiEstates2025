
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dietiEstates.backend.dto.request.AgencyRegistrationDto;
import com.dietiEstates.backend.service.AgencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agencies")
@RequiredArgsConstructor
@Slf4j
public class AgencyController
{
    private final AgencyService agencyService;
    

    //@PreAuthorize("sd")
    @PostMapping
    public ResponseEntity<Void> createAgency(@RequestBody AgencyRegistrationDto aagencyRegistrationDto) 
    {
        agencyService.createAgency(aagencyRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}