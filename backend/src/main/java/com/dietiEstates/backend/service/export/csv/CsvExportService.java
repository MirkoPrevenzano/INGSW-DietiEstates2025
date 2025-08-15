
package com.dietiEstates.backend.service.export.csv;

import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.service.export.ExportingResult;


public interface CsvExportService 
{
    public ExportingResult exportCsvReport(Agent agent);
}