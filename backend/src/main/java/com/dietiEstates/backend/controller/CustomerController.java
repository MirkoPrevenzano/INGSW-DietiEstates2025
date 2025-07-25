
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController 
{
    private final CustomerService customerService;

    
    @GetMapping("{username}/view/{realEstateId}")
    public ResponseEntity<RealEstateCreationDTO> viewRealEstate(@PathVariable("username") String username, @PathVariable("realEstateId") Long realEstateId, Authentication authentication) 
    {
        return ResponseEntity.ok().body(customerService.viewRealEstate(username, realEstateId,authentication));
    }
}