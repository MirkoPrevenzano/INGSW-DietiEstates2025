
package com.dietiEstates.backend.service.export.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
// import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiEstates.backend.enums.ExportingFormat;
import com.dietiEstates.backend.exception.ChartServiceException;
import com.dietiEstates.backend.model.embeddable.AgentStats;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.service.chart.MonthlyDealsBarChartService;
import com.dietiEstates.backend.service.chart.SuccessRatePieChartService;
import com.dietiEstates.backend.service.chart.TotalDealsPieChartService;
import com.dietiEstates.backend.service.export.ExportingResult;
import com.dietiEstates.backend.service.export.ExportServiceTemplate;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PdfExportServiceOpenPdfImpl extends ExportServiceTemplate implements PdfExportService
{
    private final SuccessRatePieChartService successRatePieChartService;
    private final TotalDealsPieChartService totalDealsPieChartService;
    private final MonthlyDealsBarChartService monthlyDealsBarChartService;


    public PdfExportServiceOpenPdfImpl(RealEstateRepository realEstateRepository, 
                                SuccessRatePieChartService successRatePieChartService,
                                TotalDealsPieChartService totalDealsPieChartService, MonthlyDealsBarChartService monthlyDealsBarChartService) 
    {
        super(realEstateRepository);
        this.successRatePieChartService = successRatePieChartService;
        this.totalDealsPieChartService = totalDealsPieChartService;
        this.monthlyDealsBarChartService = monthlyDealsBarChartService;
    }


    @Override
    public ExportingResult exportPdfReport(Agent agent) 
    {
        return super.exportReport(agent);
    }

    @Override
    protected Object initializeWriter() throws Exception 
    {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //FileOutputStream fileOutputStream = new FileOutputStream("ooooooooo.pdf");

        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
        //PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        
        
        writer.setPageEvent(new PdfPageEventHelperImpl());
        
        document.open();
        
        // Creo gli stili una sola volta e li passo tramite il wrapper
        PdfStyleConfig styleConfig = createStyleConfig();
        
        //return new PdfWriterWrapper(document, writer, styleConfig, fileOutputStream); 
        return new PdfWriterWrapper(document, writer, styleConfig, byteArrayOutputStream); 
    }

    @Override
    protected void writeAgentInfo(Agent agent, Object writer) throws Exception 
    {
        PdfWriterWrapper pdfWrapper = (PdfWriterWrapper) writer;
        Document document = pdfWrapper.getDocument();
        PdfStyleConfig styles = pdfWrapper.getStyleConfig();
        
        Paragraph agentInfoParagraph = createParagraph(styles.paragraphFont, "AGENT INFO");
        PdfPTable agentInfoTable = createTable(3, 80f, new float[] {2.5f, 2.5f, 2.5f});
        
        String[] agentInfoHeader = {"Name", "Surname", "Username"};
        writeInTable(agentInfoTable, styles.cellHeader, styles.cellHeaderFont, agentInfoHeader);
        
        String[] agentInfo = {agent.getName(), agent.getSurname(), agent.getUsername()};
        writeInTable(agentInfoTable, styles.cell, styles.cellFont, agentInfo);
        
        document.add(agentInfoParagraph);
        document.add(agentInfoTable);        
    }

    @Override
    protected void writeAgentStats(Agent agent, Object writer) throws Exception 
    {
        PdfWriterWrapper pdfWrapper = (PdfWriterWrapper) writer;
        Document document = pdfWrapper.getDocument();
        PdfStyleConfig styles = pdfWrapper.getStyleConfig();
        
        Paragraph agentStatsParagraph = createParagraph(styles.paragraphFont, "AGENT STATS");
        PdfPTable agentStatsTable = createTable(5, 100f, new float[] {2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        
        String[] agentStatsHeader = {"Uploaded Real Estates", "Sold Real Estates", "Rented Real Estates", "Sales Income", "Rentals Income"};
        writeInTable(agentStatsTable, styles.cellHeader, styles.cellHeaderFont, agentStatsHeader);
        
        AgentStats agentStats = agent.getAgentStats();
        String[] stats = {
            String.valueOf(agentStats.getTotalUploadedRealEstates()),
            String.valueOf(agentStats.getTotalSoldRealEstates()),
            String.valueOf(agentStats.getTotalRentedRealEstates()),
            String.valueOf(agentStats.getSalesIncome()),
            String.valueOf(agentStats.getRentalsIncome())
        };
        writeInTable(agentStatsTable, styles.cell, styles.cellFont, stats);
        
        document.add(agentStatsParagraph);
        document.add(agentStatsTable);
        
        addPieCharts(document, agent);        
    }

    @Override
    protected void writeRealEstateStats(Agent agent, Object writer) throws Exception 
    {
        PdfWriterWrapper pdfWrapper = (PdfWriterWrapper) writer;
        Document document = pdfWrapper.getDocument();
        PdfStyleConfig styles = pdfWrapper.getStyleConfig();
        
        Paragraph realEstateStatsParagraph = createParagraph(styles.paragraphFont, "REAL ESTATES STATS");
        PdfPTable realEstateStatsTable = createTable(5, 105f, new float[] {3.0f, 2.5f, 2.0f, 2.0f, 2.0f});
        
        String[] realEstateStatsHeader = {"Title", "Uploading Date", "Views Number", "Offers Number", "Visits Number"};
        writeInTable(realEstateStatsTable, styles.cellHeader, styles.cellHeaderFont, realEstateStatsHeader);
        
        List<AgentDashboardRealEstateStatsDto> agentDashboardRealEstateStatsDtos = this.getAgentDashboardRealEstateStatsByAgent(agent);

        if (!agentDashboardRealEstateStatsDtos.isEmpty()) 
        {
            for (AgentDashboardRealEstateStatsDto agentDashboardRealEstateStatsDto : agentDashboardRealEstateStatsDtos) 
            {
                //RealEstateStats realEstateStats = realEstate.getRealEstateStats();
                String[] estateStats = {
                    agentDashboardRealEstateStatsDto.getTitle(),
                    agentDashboardRealEstateStatsDto.getUploadingDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss")),
                    String.valueOf(agentDashboardRealEstateStatsDto.getViewsNumber()),
                    String.valueOf(agentDashboardRealEstateStatsDto.getOffersNumber()),
                    String.valueOf(agentDashboardRealEstateStatsDto.getVisitsNumber())
                };
                writeInTable(realEstateStatsTable, styles.cell, styles.cellFont, estateStats);
            }
        } else {
            writeInTable(realEstateStatsTable, styles.cell, styles.cellFont, new String[] {"//", "//", "//", "//", "//"});
        }
        
        document.add(realEstateStatsParagraph);
        document.add(realEstateStatsTable);        
    }

    @Override
    protected void writeRealEstatePerMonthStats(Agent agent, Object writer) throws Exception 
    {
        PdfWriterWrapper pdfWrapper = (PdfWriterWrapper) writer;
        Document document = pdfWrapper.getDocument();
        
        addBarChart(document, agent);        
    }

    @Override
    protected void writeSectionSeparator(Object writer) throws Exception 
    {        
    }

    @Override
    protected byte[] finalizeWriter(Object writer) throws Exception {
        PdfWriterWrapper pdfWrapper = (PdfWriterWrapper) writer;
        pdfWrapper.getDocument().close();
        return ((ByteArrayOutputStream) pdfWrapper.getOutputStream()).toByteArray();
        //return null;
    }

    @Override
    protected ExportingFormat getExportingFormat() 
    {
        return ExportingFormat.PDF;
    }

    @Override
    protected MediaType getContentType() 
    {
        return ExportingFormat.PDF.getMediaType();
    }
    
    @Override
    protected String getFileExtension() 
    {
        return ExportingFormat.PDF.getFileExtension();
    }


    // Metodi helper per PDF
    private PdfStyleConfig createStyleConfig() 
    {
        Font paragraphFont = createFont(FontFactory.HELVETICA_BOLD, 22, Color.BLUE);
        Font cellHeaderFont = createFont(FontFactory.HELVETICA, 13, Color.WHITE);
        Font cellFont = createFont(FontFactory.HELVETICA, 12, Color.BLACK);
        PdfPCell cellHeader = createCell(Color.BLUE, 5);
        PdfPCell cell = createCell(Color.WHITE, 5);
        
        return new PdfStyleConfig(paragraphFont, cellHeaderFont, cellFont, cellHeader, cell);
    }
    
    private Font createFont(String fontName, float size, Color fontColor) 
    {
        Font font = FontFactory.getFont(fontName);
        font.setSize(size);
        font.setColor(fontColor);
        return font;
    }
    
    private PdfPCell createCell(Color cellColor, float paddingSize) 
    {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(cellColor);
        cell.setPadding(paddingSize);
        return cell;
    }
    
    private Paragraph createParagraph(Font fontName, String title) 
    {
        Paragraph p = new Paragraph(title, fontName);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        return p;
    }
    
    private PdfPTable createTable(int columnsNumber, float widthPercentage, float[] relativeWidths) 
    {
        PdfPTable table = new PdfPTable(columnsNumber);
        table.setWidthPercentage(widthPercentage);
        table.setWidths(relativeWidths);
        table.setSpacingBefore(15);
        table.setSpacingAfter(40);
        return table;
    }
    
    private void writeInTable(PdfPTable table, PdfPCell cell, Font cellFont, String... headers) 
    {
        for (String header : headers) 
        {
            cell.setPhrase(new Phrase(header, cellFont));
            table.addCell(cell);
        }
    }
    
    private void addLogoHeader(Document document) 
    {
        try 
        {
            Image logo = Image.getInstance("Screenshot from 2025-01-26 00-42-55.png");
            PdfPTable logoTable = createTable(1, 60, new float[] {1});
            PdfPCell logoCell = createCell(Color.WHITE, 0);
            logoCell.setImage(logo);
            logoTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            logoTable.addCell(logoCell);
            document.add(logoTable);
        } 
        catch (Exception e) 
        {
            log.warn("Exception occurred while creating PDF image of the logo: {}", e.getMessage());
        }
    }
    
    private void addPieCharts(Document document, Agent agent) 
    {
        try 
        {
            Image im2 = Image.getInstance(successRatePieChartService.createChart(agent));
            Image im3 = Image.getInstance(totalDealsPieChartService.createChart(agent));
            PdfPTable chartTable = createTable(2, 110, new float[] {1, 1});
            PdfPCell cell = createCell(Color.WHITE, 0);
            cell.setBorderColor(Color.WHITE);
            cell.setImage(im2);
            chartTable.addCell(cell);
            
            cell.setImage(im3);
            chartTable.addCell(cell);
            
            document.add(chartTable);
        } 
        catch (ChartServiceException e)
        {
            log.warn(e.getMessage());
        }
        catch (Exception e) 
        {
            log.warn("Exception occurred while creating PDF image of Pie Charts: {}", e.getMessage());
        }
    }
    
    private void addBarChart(Document document, Agent agent) 
    {
        try 
        {
            Image im4 = Image.getInstance(monthlyDealsBarChartService.createChart(agent));
            im4.setAlignment(Element.ALIGN_CENTER);
            im4.scalePercent(80);
            document.add(im4);
        } 
        catch (ChartServiceException e)
        {
            log.warn(e.getMessage());
        }
        catch (Exception e) 
        {
            log.warn("Exception occurred while creating PDF image of Bar Chart: {}", e.getMessage());
        }
    }
    
    private void addFooter(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(520);
        table.setWidths(new int[]{50, 50});
        
        Paragraph title = new Paragraph("Exporting date: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE), 
                                       new Font(Font.HELVETICA, 10));
        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setPaddingTop(10);
        titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        titleCell.setBorderColor(Color.GRAY);
        titleCell.setBorder(Rectangle.TOP);
        table.addCell(titleCell);
        
        Paragraph pageNumberText = new Paragraph("Page " + document.getPageNumber(), 
                                               new Font(Font.HELVETICA, 10));
        PdfPCell pageNumberCell = new PdfPCell(pageNumberText);
        pageNumberCell.setPaddingTop(10);
        pageNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pageNumberCell.setBorderColor(Color.GRAY);
        pageNumberCell.setBorder(Rectangle.TOP);
        table.addCell(pageNumberCell);
        
        table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
    }



    private final class PdfPageEventHelperImpl extends PdfPageEventHelper 
    {
        
        @Override
        public void onOpenDocument(PdfWriter arg0, Document arg1) 
        {
            addLogoHeader(arg1);
        }


        @Override
        public void onStartPage(PdfWriter arg0, Document arg1) 
        {
            super.onStartPage(arg0, arg1);
        }


        @Override
        public void onEndPage(PdfWriter writer, Document document) 
        {
            addFooter(writer, document);
        }
    }


    // Classe helper per wrappare Document, PdfWriter e StyleConfig
    @RequiredArgsConstructor
    @Getter
    private static class PdfWriterWrapper 
    {
        private final Document document;
        private final PdfWriter writer;
        private final PdfStyleConfig styleConfig;
        private final OutputStream outputStream;
    }
    

    // Classe per contenere la configurazione degli stili
    @RequiredArgsConstructor
    private static class PdfStyleConfig 
    {
        public final Font paragraphFont;
        public final Font cellHeaderFont;
        public final Font cellFont;
        public final PdfPCell cellHeader;
        public final PdfPCell cell;
    }
}