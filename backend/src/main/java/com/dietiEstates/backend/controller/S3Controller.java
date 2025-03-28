
package com.dietiEstates.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dietiEstates.backend.service.RealEstateAgentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.List;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping(path = "/S3")
@RequiredArgsConstructor
@Slf4j
public class S3Controller 
{
    private final RealEstateAgentService realEstateAgentService;
    


    @PostMapping(value = "{username}/upload-photo/{realEstateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPhoto(@PathVariable("username") String username, 
                                              @RequestParam("photos") MultipartFile[] file, 
                                              @PathVariable("realEstateId") Long realEstateId)
    {
        try 
        {
            realEstateAgentService.uploadPhoto(username, file, realEstateId);
            return ResponseEntity.ok().build();            
        } 
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().header("Error", e.getMessage()).build();
        }
        catch (RuntimeException e) 
        {
            return ResponseEntity.internalServerError().header("Error", e.getMessage() + " -> " + e.getCause().getMessage()).build();
        }
    }

   
    @GetMapping(value = "get-photos/{realEstateId}")
    public ResponseEntity<String[]> getPhoto(@PathVariable("realEstateId") Long realEstateId) 
    {        
        try 
        {
            return ResponseEntity.ok()
                                .body(realEstateAgentService.getPhoto(realEstateId));
        } 
        catch (NoSuchKeyException e) 
        {
            return ResponseEntity.notFound().header("Error", e.getMessage()).build();
        }              
        catch (SdkException e) 
        {
            return ResponseEntity.internalServerError().header("Error", e.getMessage()).body(null);
        }
        catch (IOException e) 
        {
            return ResponseEntity.internalServerError().header("Error", e.getMessage()).body(null);
        } 
    }
}
