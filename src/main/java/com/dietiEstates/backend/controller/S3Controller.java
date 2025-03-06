
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
    public ResponseEntity<String> uploadPhoto(@PathVariable("username") String username, 
                                              @RequestParam("file") MultipartFile file, //MultipartFile[]
                                              @PathVariable("realEstateId") Long realEstateId)
    {
        try 
        {
            return ResponseEntity.ok().body(realEstateAgentService.uploadPhoto(username, file, realEstateId));            
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


    @GetMapping(value = "get-photo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity< byte[]> getPhoto(@RequestParam("file") String photoKey) 
    {        
        try 
        {
            byte[] photo = realEstateAgentService.getPhoto(photoKey);

            return ResponseEntity.ok()
                                 .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+photoKey+"\"")
                                 .body(photo);
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
