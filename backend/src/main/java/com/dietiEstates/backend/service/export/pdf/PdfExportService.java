
package com.dietiEstates.backend.service.export.pdf;

import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.service.export.ExportingResult;


public interface PdfExportService 
{
    public ExportingResult exportPdfReport(Agent agent);
}