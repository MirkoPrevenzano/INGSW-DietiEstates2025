
package com.dietiestates.backend.service.export.csv;

import com.dietiestates.backend.model.entity.Agent;
import com.dietiestates.backend.service.export.ExportingResult;


public interface CsvExportService 
{
    public ExportingResult exportCsvReport(Agent agent);
}