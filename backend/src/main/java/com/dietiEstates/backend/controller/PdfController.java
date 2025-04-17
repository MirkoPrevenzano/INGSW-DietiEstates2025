
package com.dietiEstates.backend.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dietiEstates.backend.service.PdfService;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
@Slf4j
public class PdfController 
{
    private final PdfService pdfService;
       
    
    //Il controller bisogna che chiama solo il metodo del service
    @GetMapping("{username}/exportPDF")
    public void exportToPDF(@PathVariable("username") String username, HttpServletResponse response) throws DocumentException, IOException 
    {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+username + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue); 

        try 
        {  
            pdfService.writePdfResponse(username, response);
        } 
        catch (DocumentException | IOException e) 
        {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setHeader("Error", "Errore durante l'esportazione del PDF!"); 
        }
    }
}