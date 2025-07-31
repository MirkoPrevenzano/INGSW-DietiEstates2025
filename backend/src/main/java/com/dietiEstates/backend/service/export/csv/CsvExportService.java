
package com.dietiEstates.backend.service.export.csv;

import com.dietiEstates.backend.service.export.ExportReportWrapper;

import jakarta.servlet.http.HttpServletResponse;


public interface CsvExportService 
{
    //public void exportCsvReport(String username, HttpServletResponse response);
    public ExportReportWrapper exportCsvReport(String username);
}