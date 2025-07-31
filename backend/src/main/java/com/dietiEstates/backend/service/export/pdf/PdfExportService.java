
package com.dietiEstates.backend.service.export.pdf;

import com.dietiEstates.backend.service.export.ExportingResult;

import jakarta.servlet.http.HttpServletResponse;


public interface PdfExportService 
{
    //public void exportPdfReport(String username, HttpServletResponse response);
    public ExportingResult exportPdfReport(String username);
}