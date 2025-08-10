
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dietiEstates.backend.dto.request.AgencyRegistrationDTO;
import com.dietiEstates.backend.service.AgencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agency")
@RequiredArgsConstructor
@Slf4j
public class AgencyController
{
    private final AgencyService agencyService;
    

    @PreAuthorize("sd")
    @PostMapping
    public ResponseEntity<Void> createAgency(@RequestBody AgencyRegistrationDTO agencyRegistrationDTO) 
    {
        agencyService.createAgency(agencyRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}