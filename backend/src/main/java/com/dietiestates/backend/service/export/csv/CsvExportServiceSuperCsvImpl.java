
package com.dietiestates.backend.service.export.csv;

import java.io.IOException;
import java.io.StringWriter;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.dietiestates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiestates.backend.enums.ExportingFormat;
import com.dietiestates.backend.enums.MonthLabel;
import com.dietiestates.backend.model.embeddable.AgentStats;
import com.dietiestates.backend.model.entity.Agent;
import com.dietiestates.backend.repository.RealEstateRepository;
import com.dietiestates.backend.service.export.ExportServiceTemplate;
import com.dietiestates.backend.service.export.ExportingResult;
import com.dietiestates.backend.service.mock.MockingStatsService;

import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.FmtNumber;
import org.supercsv.cellprocessor.constraint.DMinMax;
import org.supercsv.cellprocessor.constraint.LMinMax;
import org.supercsv.cellprocessor.constraint.StrMinMax;
import org.supercsv.cellprocessor.constraint.StrNotNullOrEmpty;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CsvExportServiceSuperCsvImpl extends ExportServiceTemplate implements CsvExportService
{
    private final MockingStatsService mockingStatsService;

    

    public CsvExportServiceSuperCsvImpl(RealEstateRepository realEstateRepository, MockingStatsService mockingStatsService) 
    {
        super(realEstateRepository);
        this.mockingStatsService = mockingStatsService;
    }



    @Override
    public ExportingResult exportCsvReport(Agent agent) 
    {
        return super.exportReport(agent);
    }



    @Override
    protected Object initializeWriter() 
    {
        StringWriter stringWriter = new StringWriter();
        
        ICsvListWriter csvListWriter = new CsvListWriter(stringWriter, CsvPreference.EXCEL_PREFERENCE);
        
        return new CsvWriterWrapper(csvListWriter, stringWriter);
    }


    @Override
    protected void writeAgentInfo(Agent agent, Object writer) 
    {        
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;

        ICsvListWriter csvListWriter = csvWriterWrapper.getCsvListWriter();

        List<Object> agentInfoData = Arrays.asList(agent.getName(),
                                                   agent.getSurname(), 
                                                   agent.getUsername());
        
        try 
        {
            csvListWriter.writeHeader("AGENT INFO");
            csvListWriter.writeHeader("Name", "Surname", "Email");

            csvListWriter.write(agentInfoData, getAgentInfoProcessors());
        } 
        catch (IOException e) 
        {
            throw new SuperCsvException(e.getMessage());
        }        
    }


    @Override
    protected void writeAgentStats(Agent agent, Object writer) 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;

        AgentStats agentStats = agent.getAgentStats();
        List<Object> agentStatsData = Arrays.asList(agentStats.getTotalUploadedRealEstates(),
                                                    agentStats.getTotalSoldRealEstates(),
                                                    agentStats.getTotalRentedRealEstates(),
                                                    agentStats.getSalesIncome(),
                                                    agentStats.getRentalsIncome(),
                                                    agentStats.getTotalIncomes(),
                                                    agentStats.getSuccessRate());

        try 
        {
            csvWriterWrapper.getCsvListWriter().writeHeader("AGENT STATS");
            csvWriterWrapper.getCsvListWriter().writeHeader("Uploaded RealEstates", "Sold Real Estates", "Rented Real Estates", 
                                                            "Sales Income", "Rentals Income", 
                                                            "Total Incomes", "Success Rate");
            
            csvWriterWrapper.getCsvListWriter().write(agentStatsData, getAgentStatsProcessors());
        } 
        catch (IOException e) 
        {
            throw new SuperCsvException(e.getMessage());
        }        
    }


    @Override
    protected void writeRealEstateStats(Agent agent, Object writer) 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;

        List<AgentDashboardRealEstateStatsDto> agentDashboardRealEstateStatsDtos = this.getAgentDashboardRealEstateStats(agent);
        
        try 
        {
            csvWriterWrapper.getCsvListWriter().writeHeader("REAL ESTATES STATS");
            csvWriterWrapper.getCsvListWriter().writeHeader("Title", "Uploading Date", "Views Number", "Visits Number", "Offers Number");

            if (!agentDashboardRealEstateStatsDtos.isEmpty()) 
            {
                for (AgentDashboardRealEstateStatsDto agentDashboardRealEstateStatsDto : agentDashboardRealEstateStatsDtos) 
                {
                    List<Object> realEstateStatsData = Arrays.asList(agentDashboardRealEstateStatsDto.getTitle(),
                                                                     Date.from(agentDashboardRealEstateStatsDto.getUploadingDate().toInstant(ZoneOffset.UTC)),
                                                                     agentDashboardRealEstateStatsDto.getViewsNumber(),
                                                                     agentDashboardRealEstateStatsDto.getVisitsNumber(),
                                                                     agentDashboardRealEstateStatsDto.getOffersNumber());

                    csvWriterWrapper.getCsvListWriter().write(realEstateStatsData, getRealEstateStatsProcessors());
                }
            } 
            else 
            {
                csvWriterWrapper.getCsvListWriter().write("//", "//", "//", "//", "//");
            }
        } 
        catch (IOException e) 
        {
            throw new SuperCsvException(e.getMessage());
        }        
    }


    @Override
    protected void writeRealEstateMonthlyDeals(Agent agent, Object writer) 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;

        Integer[] monthlyStats = mockingStatsService.mockBarChartStats(agent);
        List<Integer> monthlyData = Arrays.asList(monthlyStats);

        try 
        {
            csvWriterWrapper.getCsvListWriter().writeHeader("REAL ESTATES MONTHLY DEALS");
            csvWriterWrapper.getCsvListWriter().writeHeader(MonthLabel.getMonthAbbreviations());
            
            csvWriterWrapper.getCsvListWriter().write(monthlyData);
        } 
        catch (IOException e) 
        {
            throw new SuperCsvException(e.getMessage());
        }        
    }


    @Override
    protected void writeSectionSeparator(Object writer) 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;

        try 
        {
            csvWriterWrapper.getCsvListWriter().writeHeader("");
            csvWriterWrapper.getCsvListWriter().writeHeader("");
        } 
        catch (IOException e) 
        {
            throw new SuperCsvException(e.getMessage());
        }        
    }   


    @Override
    protected byte[] finalizeWriter(Object writer) 
    {
        CsvWriterWrapper csvWriterWrapper = (CsvWriterWrapper) writer;

        ICsvListWriter csvListWriter = csvWriterWrapper.getCsvListWriter();
        StringWriter stringWriter = csvWriterWrapper.getStringWriter();

        try 
        {
            csvListWriter.close();
        } 
        catch (IOException e) 
        {
            throw new SuperCsvException(e.getMessage());
        }
        
        return stringWriter.toString().getBytes();        
    }


    @Override
    protected ExportingFormat getExportingFormat() 
    {
        return ExportingFormat.CSV;
    }


    @Override
    protected MediaType getContentType() 
    {
        return ExportingFormat.CSV.getMediaType();
    }


    @Override
    protected String getFileExtension() 
    {
        return ExportingFormat.CSV.getFileExtension();
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