
package com.dietiEstates.backend.service.export;

import java.awt.Color;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.embeddable.AgentStats;
import com.dietiEstates.backend.model.embeddable.RealEstateStats;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import com.dietiEstates.backend.service.AgentService;
import com.dietiEstates.backend.service.chart.ChartService;
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

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OpenPdfExportService extends ExportServiceTemplate implements PdfExportService
{
    private final ChartService chartService;

    public OpenPdfExportService(AgentRepository agentRepository, AgentService agentService, RealEstateRepository realEstateRepository, ChartService chartService) 
    {
        super(agentRepository, agentService, realEstateRepository);
        this.chartService = chartService;
    }

    

    @Override
    public void exportPdfReport(String username, HttpServletResponse response) 
    {
        super.exportReport(username, response);
    }



    @Override
    protected void setupResponseHeaders(String username, HttpServletResponse response) 
    {
        response.setContentType("application/pdf");
        String fileName = generateFileName(username) + ".pdf";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader("Content-Disposition", headerValue);        
    }


    @Override
    protected Object initializeWriter(HttpServletResponse response) throws Exception 
    {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("looooool.pdf"));
        //PdfWriter p = PdfWriter.getInstance(document, response.getOutputStream());

        
        // Aggiungi il page event helper per header/footer
        writer.setPageEvent(new PdfPageEventHelperImpl());
        
        document.open();
        
        // Aggiungi il logo header
        addLogoHeader(document);
        
        // Crea gli stili una sola volta e li passa tramite il wrapper
        PdfStyleConfig styleConfig = createStyleConfig();
        
        return new PdfWriterWrapper(document, writer, styleConfig); 
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
        
        // Aggiungere i grafici a torta
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
        
        if (getRealEstateStatsByAgent(agent) != null) {
            for (RealEstate realEstate : agent.getRealEstates()) {
                RealEstateStats realEstateStats = realEstate.getRealEstateStats();
                String[] estateStats = {
                    realEstate.getTitle(),
                    realEstate.getUploadingDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    String.valueOf(realEstateStats.getViewsNumber()),
                    String.valueOf(realEstateStats.getOffersNumber()),
                    String.valueOf(realEstateStats.getVisitsNumber())
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
        
        // Aggiungere il grafico a barre
        addBarChart(document);        
    }


    @Override
    protected void writeSectionSeparator(Object writer) throws Exception {
        // TODO Auto-generated method stub
        
    }

    
    @Override
    protected void finalizeWriter(Object writer, HttpServletResponse response) throws Exception 
    {
        PdfWriterWrapper pdfWrapper = (PdfWriterWrapper) writer;
        pdfWrapper.getDocument().close();
        response.setHeader("Success", "PDF esportato correttamente!");        
    }

    @Override
    protected void handleExportError(Exception e, HttpServletResponse response) 
    {
        log.error("Errore durante l'esportazione del PDF: {}", e.getMessage());
        response.setStatus(500);
        response.setHeader("Error", "Errore durante l'esportazione del PDF!");        
    }



        // Metodi helper per PDF
    private PdfStyleConfig createStyleConfig() {
        Font paragraphFont = createFont(FontFactory.HELVETICA_BOLD, 22, Color.BLUE);
        Font cellHeaderFont = createFont(FontFactory.HELVETICA, 13, Color.WHITE);
        Font cellFont = createFont(FontFactory.HELVETICA, 12, Color.BLACK);
        PdfPCell cellHeader = createCell(Color.BLUE, 5);
        PdfPCell cell = createCell(Color.WHITE, 5);
        
        return new PdfStyleConfig(paragraphFont, cellHeaderFont, cellFont, cellHeader, cell);
    }
    
    private Font createFont(String fontName, float size, Color fontColor) {
        Font font = FontFactory.getFont(fontName);
        font.setSize(size);
        font.setColor(fontColor);
        return font;
    }
    
    private PdfPCell createCell(Color cellColor, float paddingSize) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(cellColor);
        cell.setPadding(paddingSize);
        return cell;
    }
    
    private Paragraph createParagraph(Font fontName, String title) {
        Paragraph p = new Paragraph(title, fontName);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        return p;
    }
    
    private PdfPTable createTable(int columnsNumber, float widthPercentage, float[] relativeWidths) {
        PdfPTable table = new PdfPTable(columnsNumber);
        table.setWidthPercentage(widthPercentage);
        table.setWidths(relativeWidths);
        table.setSpacingBefore(15);
        table.setSpacingAfter(40);
        return table;
    }
    
    private void writeInTable(PdfPTable table, PdfPCell cell, Font cellFont, String... headers) {
        for (String header : headers) {
            cell.setPhrase(new Phrase(header, cellFont));
            table.addCell(cell);
        }
    }
    
    private void addLogoHeader(Document document) {
        try {
            Image logo = Image.getInstance("Screenshot from 2025-01-26 00-42-55.png");
            PdfPTable logoTable = createTable(1, 60, new float[] {1});
            PdfPCell logoCell = createCell(Color.WHITE, 0);
            logoCell.setImage(logo);
            logoTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            logoTable.addCell(logoCell);
            document.add(logoTable);
        } catch (Exception e) {
            log.warn("Could not add logo: {}", e.getMessage());
        }
    }
    
    private void addPieCharts(Document document, Agent agent) {
        try {
            Image im2 = Image.getInstance(chartService.createPieChart(agent));
            Image im3 = Image.getInstance(chartService.createPieChart2(agent));
            PdfPTable chartTable = createTable(2, 110, new float[] {1, 1});
            
            PdfPCell cell = createCell(Color.WHITE, 0);
            cell.setBorderColor(Color.WHITE);
            cell.setImage(im2);
            chartTable.addCell(cell);
            
            cell.setImage(im3);
            chartTable.addCell(cell);
            
            document.add(chartTable);
        } catch (Exception e) {
            log.warn("Could not add pie charts: {}", e.getMessage());
        }
    }
    
    private void addBarChart(Document document) {
        try {
            Image im4 = Image.getInstance(chartService.createBarChart());
            im4.setAlignment(Element.ALIGN_CENTER);
            im4.scalePercent(80);
            document.add(im4);
        } catch (Exception e) {
            log.warn("Could not add bar chart: {}", e.getMessage());
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
        public void onOpenDocument(PdfWriter arg0, Document arg1) {
            // TODO Auto-generated method stub
            super.onOpenDocument(arg0, arg1);
        }


        @Override
        public void onStartPage(PdfWriter arg0, Document arg1) {
            // TODO Auto-generated method stub
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