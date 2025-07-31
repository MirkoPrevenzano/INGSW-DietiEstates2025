
package com.dietiEstates.backend.service.export.pdf;

import com.dietiEstates.backend.service.export.ExportReportWrapper;

import jakarta.servlet.http.HttpServletResponse;


public interface PdfExportService 
{
    //public void exportPdfReport(String username, HttpServletResponse response);
    public ExportReportWrapper exportPdfReport(String username);
}