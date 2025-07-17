
package com.dietiEstates.backend.service.export.csv;

import jakarta.servlet.http.HttpServletResponse;


public interface CsvExportService 
{
    public void exportCsvReport(String username, HttpServletResponse response);
}