
package com.dietiEstates.backend.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.AgentService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class CsvHelper
{
    private final AgentRepository agentRepository;
    private final AgentService agentService;



    public void writeCsvResponse(String username, HttpServletResponse response) throws IOException 
    {   
        Agent agent = agentRepository.findByUsername(username).get();

        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date()); 
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + username + "_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        ICsvListWriter listWriter = null;
        //ICsvListWriter listWriter2 = null;
        try 
        {            
            listWriter = new CsvListWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);  
            //listWriter2 = new CsvListWriter(new FileWriter("sfsfs.csv"), CsvPreference.EXCEL_PREFERENCE);

            writeAgentInfo(agent, listWriter);

            listWriter.writeHeader("");
            listWriter.writeHeader("");

            writeAgentStats(agent, listWriter);

            listWriter.writeHeader("");
            listWriter.writeHeader("");

            writeRealEstatesStats(agent, listWriter);

            listWriter.writeHeader("");
            listWriter.writeHeader("");

            writeRealEstatesPerMonthStats(agent, listWriter);

            log.info("CSV esportato correttamente");  
        }
        catch (SuperCsvException | IOException e) 
        {
            response.setStatus(400);
            response.setHeader( "Error", "Errore durante l'esportazione del file CSV!");
            log.info("Errore durante l'esportazione del file CSV! " + e.getMessage());  
        }
        finally 
        {
            if( listWriter != null ) 
            {
                listWriter.close();
            }
        }
    }



    private void writeAgentInfo(Agent agent, ICsvListWriter listWriter) throws IOException 
    {
        listWriter.writeHeader("AGENT INFO");
        final String[] agentHeader = {"Name", "Surname", "Username"};
        listWriter.writeHeader(agentHeader);
        final CellProcessor[] agentProcessors = getAgentProcessors();
        List<Object> listObjects = Arrays.asList(new Object[] {agent.getName(), 
                                                               agent.getSurname(),
                                                               agent.getUsername()});   

        listWriter.write(listObjects, agentProcessors);
    }
    

    private void writeAgentStats(Agent agent, ICsvListWriter listWriter) throws IOException 
    {
        listWriter.writeHeader("AGENT STATS");
        final String[] agentStatsHeader = {"TotalUploadedRealEstates", "TotalSoldRealEstates", "TotalRentedRealEstates", 
                                           "SalesIncome", "RentalsIncome", "Total Incomes", "Success Rate"};
        listWriter.writeHeader(agentStatsHeader);
        final CellProcessor[] agentStatsProcessors = getAgentStatsProcessors();
        List<Object> listObjects = Arrays.asList(new Object[] {agent.getAgentStats().getTotalUploadedRealEstates(), 
                                                               agent.getAgentStats().getTotalSoldRealEstates(),
                                                               agent.getAgentStats().getTotalRentedRealEstates(),
                                                               agent.getAgentStats().getSalesIncome(),
                                                               agent.getAgentStats().getRentalsIncome(),
                                                               agent.getAgentStats().getTotalIncomes(),
                                                               agent.getAgentStats().getSuccessRate()});  

        listWriter.write(listObjects, agentStatsProcessors);
    }


    private void writeRealEstatesStats(Agent agent, ICsvListWriter listWriter) throws IOException 
    {
        listWriter.writeHeader("REAL ESTATES STATS");
        final String[] realEstateStatsHeader = {"Title", "UploadingDate", "ViewsNumber", "VisitsNumber", "OffersNumber"};
        final CellProcessor[] realEstateProcessors = getEstateStatsProcessors();
        listWriter.writeHeader(realEstateStatsHeader);
        List<RealEstate> realEstates = agent.getRealEstates();
        if(realEstates.size() > 0)
        {
            for( final RealEstate realEstate : realEstates ) 
            {
                 List<Object> listObjects = Arrays.asList(new Object[] {realEstate.getTitle(),
                                                                        realEstate.getUploadingDate(),
                                                                        realEstate.getRealEstateStats().getViewsNumber(), 
                                                                        realEstate.getRealEstateStats().getVisitsNumber(), 
                                                                        realEstate.getRealEstateStats().getOffersNumber()});   
    
                 listWriter.write(listObjects, realEstateProcessors);
            }
        }
        else
            listWriter.write("//","//","//","//","//");
    }


    private void writeRealEstatesPerMonthStats(Agent agent, ICsvListWriter listWriter) throws IOException 
    {
        listWriter.writeHeader("REAL ESTATES STATS PER MONTH");
        final String[] realEstateStatsHeader = {"JAN", "FEB", "MAR", "APR", "MAY", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        listWriter.writeHeader(realEstateStatsHeader);
        Integer[] aa = agentService.getBarChartStats();
        
        List<Integer> listObjects = Arrays.asList(aa);   
        listWriter.write(listObjects);
        
    }


    private CellProcessor[] getAgentProcessors() 
    {
        final CellProcessor[] processors = new CellProcessor[] 
        {
            new NotNull(), // name
            new NotNull(), // surname
            new NotNull()  // username
        };
        
        return processors;
    }


    private CellProcessor[] getAgentStatsProcessors() 
    {
        final CellProcessor[] processors = new CellProcessor[] 
        {
            new NotNull(),  // totalUploadedRealEstates
            new NotNull(),  // totalSoldRealEstates
            new NotNull(),  // totalRentedRealEstates
            new NotNull(),  // salesIncome
            new NotNull(),  // rentalsIncomes
            new NotNull(),  // totalIncomes
            new NotNull()   // successRate
        };
        
        return processors;
    }


    private CellProcessor[] getEstateStatsProcessors() 
    {
        final CellProcessor[] processors = new CellProcessor[] 
        { 
            new NotNull(), // title
            new NotNull(), // uploadingDate
            new NotNull(), // viewsNumber
            new NotNull(), // visitsNumber
            new NotNull()  // offersNumber
        };
        
        return processors;
    }
}
