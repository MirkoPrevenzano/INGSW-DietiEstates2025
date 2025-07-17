
package com.dietiEstates.backend.service.export.pdf;

import jakarta.servlet.http.HttpServletResponse;


public interface PdfExportService 
{
    public void exportPdfReport(String username, HttpServletResponse response);
}