
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.RealEstateCompleteDTO;
import com.dietiEstates.backend.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController 
{
    private final CustomerService customerService;


    @GetMapping("{username}/view/{realEstateId}")
    public ResponseEntity<RealEstateCompleteDTO> viewRealEstate(@PathVariable("username") String username, @PathVariable("realEstateId") Long realEstateId) 
    {
        return ResponseEntity.ok().body(customerService.viewRealEstate(username, realEstateId));
    }
}