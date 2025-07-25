
package com.dietiEstates.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dietiEstates.backend.service.AgentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/S3")
@RequiredArgsConstructor
@Slf4j
public class S3Controller 
{
    private final AgentService agentService;    


    @PostMapping(value = "{username}/upload-photo2/{realEstateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPhoto2(@PathVariable("username") String username, 
                                              @RequestParam("photos") MultipartFile[] file, 
                                              @PathVariable("realEstateId") Long realEstateId) throws IOException
    {
        agentService.uploadPhoto2(username, file, realEstateId);
        return ResponseEntity.ok().build();            
    }


    @GetMapping(value = "get-photos2/{realEstateId}")
    public ResponseEntity<List<String>> getPhoto2(@PathVariable("realEstateId") Long realEstateId) throws IOException
    {        
        return ResponseEntity.ok(agentService.getPhoto2(realEstateId));
    }




  /*   @PostMapping(value = "{username}/upload-photo/{realEstateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPhoto(@PathVariable("username") String username, 
                                              @RequestParam("photos") MultipartFile[] file, 
                                              @PathVariable("realEstateId") Long realEstateId)
    {
        try 
        {
            agentService.uploadPhoto(username, file, realEstateId);
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
            String[] photos = agentService.getPhoto(realEstateId);
            return ResponseEntity.ok(photos);
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
    } */
}