/* 
package com.dietiEstates.backend.service.export.csv;

import java.io.FileWriter;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.FmtNumber;
import org.supercsv.cellprocessor.constraint.DMinMax;
import org.supercsv.cellprocessor.constraint.LMinMax;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrMinMax;
import org.supercsv.cellprocessor.constraint.StrNotNullOrEmpty;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.dietiEstates.backend.model.embeddable.AgentStats;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.service.export.ExportServiceTemplate;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CsvExportServiceSuperCsvImpl extends ExportServiceTemplate implements CsvExportService
{
    public CsvExportServiceSuperCsvImpl(AgentRepository agentRepository, AgentService agentService, RealEstateRepository realEstateRepository) 
    {
        super(agentRepository, agentService, realEstateRepository);
    }


    


    @Override
    public void exportCsvReport(String username, HttpServletResponse response) 
    {
        super.exportReport(username, response);        
    }



    @Override
    protected void setupResponseHeaders(String username, HttpServletResponse response) 
    {
        response.setContentType("text/csv");
        String fileName = generateFileName(username) + ".csv";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader("Content-Disposition", headerValue);        
    }


    @Override
    protected Object initializeWriter(HttpServletResponse response) throws Exception 
    {
        return new CsvListWriter(new FileWriter("looool.csv"), CsvPreference.EXCEL_PREFERENCE);
        //listWriter = new CsvListWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);  

    }


    @Override
    protected void writeAgentInfo(Agent agent, Object writer) throws Exception 
    {
        ICsvListWriter csvWriter = (ICsvListWriter) writer;
        csvWriter.writeHeader("AGENT INFO");
        csvWriter.writeHeader("Name", "Surname", "Username");
        
        List<Object> agentData = Arrays.asList(
            agent.getName(), 
            agent.getSurname(), 
            agent.getUsername()
        );
        csvWriter.write(agentData, getAgentInfoProcessors());        
    }

    @Override
    protected void writeAgentStats(Agent agent, Object writer) throws Exception 
    {
        ICsvListWriter csvWriter = (ICsvListWriter) writer;
        csvWriter.writeHeader("AGENT STATS");
        csvWriter.writeHeader("TotalUploadedRealEstates", "TotalSoldRealEstates", 
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
        csvWriter.write(statsData, getAgentStatsProcessors());        
    }

    @Override
    protected void writeRealEstateStats(Agent agent, Object writer) throws Exception 
    {
        ICsvListWriter csvWriter = (ICsvListWriter) writer;
        csvWriter.writeHeader("REAL ESTATES STATS");
        csvWriter.writeHeader("Title", "UploadingDate", "ViewsNumber", "VisitsNumber", "OffersNumber");
        
        if (getRealEstateStatsByAgent(agent) != null) 
        {
            for (RealEstate realEstate : agent.getRealEstates()) 
            {
                List<Object> estateData = Arrays.asList(
                    realEstate.getTitle(),
                    Date.from(realEstate.getUploadingDate().toInstant(ZoneOffset.UTC)),
                    //realEstate.getUploadingDate(),
                    realEstate.getRealEstateStats().getViewsNumber(),
                    realEstate.getRealEstateStats().getVisitsNumber(),
                    realEstate.getRealEstateStats().getOffersNumber()
                );
                csvWriter.write(estateData, getRealEstateStatsProcessors());
            }
        } else {
            csvWriter.write("//", "//", "//", "//", "//");
        }        
    }


    @Override
    protected void writeRealEstatePerMonthStats(Agent agent, Object writer) throws Exception 
    {
        ICsvListWriter csvWriter = (ICsvListWriter) writer;
        csvWriter.writeHeader("REAL ESTATES STATS PER MONTH");
        csvWriter.writeHeader("JAN", "FEB", "MAR", "APR", "MAY", "JUN", 
                             "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
        
        Integer[] monthlyStats = agentService.getBarChartStats();
        List<Integer> monthlyData = Arrays.asList(monthlyStats);
        csvWriter.write(monthlyData);        
    }


    @Override
    protected void writeSectionSeparator(Object writer) throws Exception 
    {
        ICsvListWriter csvWriter = (ICsvListWriter) writer;
        csvWriter.writeHeader("");
        csvWriter.writeHeader("");        
    }   


    @Override
    protected void finalizeWriter(Object writer, HttpServletResponse response) throws Exception {
        ICsvListWriter csvWriter = (ICsvListWriter) writer;
        csvWriter.close();               
        response.setHeader("Success", "CSV esportato correttamente!");        

    }


    @Override
    protected void handleExportError(Exception e, HttpServletResponse response) 
    {
        response.setStatus(400);
        response.setHeader("Error", "Errore durante l'esportazione del file CSV!");
        log.error("Errore durante l'esportazione del file CSV: {}", e.getMessage());        
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
} */