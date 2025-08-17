
package com.dietiEstates.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.dto.request.CustomerRegistrationDto;
import com.dietiEstates.backend.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController 
{
    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<Void> customerRegistration(@Valid @RequestBody CustomerRegistrationDto userRegistrationDTO) 
    {
        customerService.customerRegistration(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    } 
}