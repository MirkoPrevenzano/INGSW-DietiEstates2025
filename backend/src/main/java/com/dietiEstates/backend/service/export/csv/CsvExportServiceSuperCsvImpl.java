
package com.dietiEstates.backend.service.export.csv;

import java.io.StringWriter;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.FmtNumber;
import org.supercsv.cellprocessor.constraint.DMinMax;
import org.supercsv.cellprocessor.constraint.LMinMax;
import org.supercsv.cellprocessor.constraint.StrMinMax;
import org.supercsv.cellprocessor.constraint.StrNotNullOrEmpty;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDTO;
import com.dietiEstates.backend.model.embeddable.AgentStats;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.service.export.ExportingResult;
import com.dietiEstates.backend.service.export.ExportServiceTemplate;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CsvExportServiceSuperCsvImpl extends ExportServiceTemplate implements CsvExportService
{
    private final MockingStatsService mockingStatsService;

    
    public CsvExportServiceSuperCsvImpl(AgentRepository agentRepository, RealEstateRepository realEstateRepository, MockingStatsService mockingStatsService) 
    {
        super(agentRepository, realEstateRepository);
        this.mockingStatsService = mockingStatsService;
    }


    @Override
    public ExportingResult exportCsvReport(String username) 
    {
        return super.exportReport(username);
    }



/*     @Override
    protected void setupResponseHeaders(String username, HttpServletResponse response) 
    {
        response.setContentType("text/csv");
        String fileName = generateFileName(username) + ".csv";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader("Content-Disposition", headerValue);        
    } */


    @Override
    protected Object initializeWriter() throws Exception 
    {
        StringWriter stringWriter = new StringWriter();
       // FileWriter fileWriter = new FileWriter("lool2.csv");
        
        ICsvListWriter csvListWriter = new CsvListWriter(stringWriter, CsvPreference.EXCEL_PREFERENCE);
        //return new CsvListWriter(stringWriter, CsvPreference.EXCEL_PREFERENCE);
        
        return new CsvWriterWrapper(csvListWriter, stringWriter);

        //listWriter = new CsvListWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);  
    }


    @Override
    protected void writeAgentInfo(Agent agent, Object writer) throws Exception 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;

        csvWriterWrapper.getCsvListWriter().writeHeader("AGENT INFO");
        csvWriterWrapper.getCsvListWriter().writeHeader("Name", "Surname", "Username");
        
        List<Object> agentData = Arrays.asList(
            agent.getName(), 
            agent.getSurname(), 
            agent.getUsername()
        );
        csvWriterWrapper.getCsvListWriter().write(agentData, getAgentInfoProcessors());        
    }

    @Override
    protected void writeAgentStats(Agent agent, Object writer) throws Exception 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;
        csvWriterWrapper.getCsvListWriter().writeHeader("AGENT STATS");
        csvWriterWrapper.getCsvListWriter().writeHeader("TotalUploadedRealEstates", "TotalSoldRealEstates", 
                             "TotalRentedRealEstates", "SalesIncome", "RentalsIncome", 
                             "Total Incomes", "Success Rate");
        
        AgentStats stats = agent.getAgentStats();
        List<Object> statsData = Arrays.asList(
            stats.getTotalUploadedRealEstates(),
            stats.getTotalSoldRealEstates(),
            stats.getTotalRentedRealEstates(),
            stats.getSalesIncome(),
            stats.getRentalsIncome(),
            stats.getTotalIncomes(),
            stats.getSuccessRate()
        );
        csvWriterWrapper.getCsvListWriter().write(statsData, getAgentStatsProcessors());        
    }

    @Override
    protected void writeRealEstateStats(Agent agent, Object writer) throws Exception 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;
        csvWriterWrapper.getCsvListWriter().writeHeader("REAL ESTATES STATS");
        csvWriterWrapper.getCsvListWriter().writeHeader("Title", "UploadingDate", "ViewsNumber", "VisitsNumber", "OffersNumber");
        
        List<AgentDashboardRealEstateStatsDTO> agentDashboardRealEstateStatsDTOs = this.getAgentDashboardRealEstateStatsByAgent(agent);

        if (!agentDashboardRealEstateStatsDTOs.isEmpty()) 
        {
            for (AgentDashboardRealEstateStatsDTO agentDashboardRealEstateStatsDTO : agentDashboardRealEstateStatsDTOs) 
            {
                List<Object> estateData = Arrays.asList(
                    agentDashboardRealEstateStatsDTO.getTitle(),
                    Date.from(agentDashboardRealEstateStatsDTO.getUploadingDate().toInstant(ZoneOffset.UTC)),
                    //realEstate.getUploadingDate(),
                    agentDashboardRealEstateStatsDTO.getViewsNumber(),
                    agentDashboardRealEstateStatsDTO.getVisitsNumber(),
                    agentDashboardRealEstateStatsDTO.getOffersNumber()
                );
                csvWriterWrapper.getCsvListWriter().write(estateData, getRealEstateStatsProcessors());
            }
        } 
        else 
        {
            log.warn("SONO IN ELSEEE");
            csvWriterWrapper.getCsvListWriter().write("//", "//", "//", "//", "//");
        }        
    }


    @Override
    protected void writeRealEstatePerMonthStats(Agent agent, Object writer) throws Exception 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;
        csvWriterWrapper.getCsvListWriter().writeHeader("REAL ESTATES STATS PER MONTH");
        csvWriterWrapper.getCsvListWriter().writeHeader("JAN", "FEB", "MAR", "APR", "MAY", "JUN", 
                             "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
        
        Integer[] monthlyStats = mockingStatsService.mockBarChartStats(agent);
        List<Integer> monthlyData = Arrays.asList(monthlyStats);
        csvWriterWrapper.getCsvListWriter().write(monthlyData);        
    }


    @Override
    protected void writeSectionSeparator(Object writer) throws Exception 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;
        csvWriterWrapper.getCsvListWriter().writeHeader("");
        csvWriterWrapper.getCsvListWriter().writeHeader("");        
    }   


    @Override
    protected byte[] finalizeWriter(Object writer) throws Exception 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;

        csvWriterWrapper.getCsvListWriter().close();
        
        String csvContent = csvWriterWrapper.getStringWriter().toString();

        return csvContent.getBytes();
        // csvWriter.close();               

        //return null;
    }




    @Override
    protected String getContentType() 
    {
        return "text/csv";
    }


    @Override
    protected String getFileExtension() 
    {
        return ".csv";
    }


    // Metodi helper per i processori CSV
    private CellProcessor[] getAgentInfoProcessors() 
    {
        return new CellProcessor[] {new StrNotNullOrEmpty(), 
                                    new StrNotNullOrEmpty(), 
                                    new UniqueHashCode()};
    }
    
    private CellProcessor[] getAgentStatsProcessors() 
    {
        return new CellProcessor[] {new LMinMax(0L, LMinMax.MAX_LONG),
                                    new LMinMax(0L, LMinMax.MAX_LONG), 
                                    new LMinMax(0L, LMinMax.MAX_LONG), 
                                    new DMinMax(0, DMinMax.MAX_DOUBLE, new FmtNumber("0.00")), 
                                    new DMinMax(0, DMinMax.MAX_DOUBLE, new FmtNumber("0.00")), 
                                    new DMinMax(0, DMinMax.MAX_DOUBLE, new FmtNumber("0.00")), 
                                    new DMinMax(0, DMinMax.MAX_DOUBLE, new FmtNumber("0.00"))}; 
    }
    
    private CellProcessor[] getRealEstateStatsProcessors() 
    {
        return new CellProcessor[] {new StrMinMax(0,35), 
                                    new FmtDate("dd-MM-yyyy  HH:mm:ss"), 
                                    new LMinMax(0L, LMinMax.MAX_LONG), 
                                    new LMinMax(0L, LMinMax.MAX_LONG), 
                                    new LMinMax(0L, LMinMax.MAX_LONG)};
    }



    @RequiredArgsConstructor
    @Getter
    private static class CsvWriterWrapper
    {
        private final ICsvListWriter csvListWriter;
        private final StringWriter stringWriter;
    }
}