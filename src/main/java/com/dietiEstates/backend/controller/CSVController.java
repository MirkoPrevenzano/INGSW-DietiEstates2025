
package com.dietiEstates.backend.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dietiEstates.backend.service.CsvService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/agent")
@RequiredArgsConstructor
@Slf4j
public class CSVController 
{
    private final CsvService csvService;


         
    @GetMapping(value = "/{username}/exportCSV")
    public void exportToCSV(@PathVariable("username") String username, HttpServletResponse response) throws IOException 
    {
        csvService.writeCsvResponse(username,response);
    }
}
