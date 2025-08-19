
package com.dietiestates.backend.service.export.pdf;

import com.dietiestates.backend.model.entity.Agent;
import com.dietiestates.backend.service.export.ExportingResult;


public interface PdfExportService 
{
    public ExportingResult exportPdfReport(Agent agent);
}