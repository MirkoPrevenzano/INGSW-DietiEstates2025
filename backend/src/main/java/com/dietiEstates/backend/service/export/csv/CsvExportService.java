
package com.dietiEstates.backend.service.export.csv;

import com.dietiEstates.backend.service.export.ExportingResult;


public interface CsvExportService 
{
    //public void exportCsvReport(String username, HttpServletResponse response);
    public ExportingResult exportCsvReport(String username);
}