
package com.dietiEstates.backend.helper;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.embeddable.AgentStats;
import com.dietiEstates.backend.model.embeddable.RealEstateStats;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class ExportPdfHelper extends PdfPageEventHelper
{
    private final AgentRepository agentRepository;

    
    public void writePdfResponse(String username, HttpServletResponse response) throws DocumentException, IOException 
    {
        Agent agent = agentRepository.findByUsername(username).get();
        
        Document document = new Document(PageSize.A4);
        try 
        {  

            PdfWriter p = PdfWriter.getInstance(document, response.getOutputStream());

            p.setPageEvent(new ExportPdfHelper(agentRepository));
            document.open();

            Font paragraphFont = createFont(FontFactory.HELVETICA_BOLD, 22, Color.BLUE);
            Font cellHeaderFont = createFont(FontFactory.HELVETICA, 13, Color.WHITE);
            Font cellFont = createFont(FontFactory.HELVETICA, 12, Color.BLACK);
            PdfPCell cellHeader = createCell(Color.BLUE, 5);
            PdfPCell cell = createCell(Color.WHITE, 5);
    

            Image im1 = Image.getInstance("Screenshot from 2025-01-26 00-42-55.png");
            PdfPTable lol2 = createTable(1, 60, new float[] {1});
            PdfPCell cell3 = createCell(Color.WHITE, 0);
            cell3.setImage(im1);
            lol2.setHorizontalAlignment(Element.ALIGN_CENTER);
            lol2.addCell(cell3);
            document.add(lol2);


            writeAgentInfo(document, paragraphFont, cellHeaderFont, cellHeader, cellFont, cell, agent);
            writeAgentStats(document, paragraphFont, cellHeaderFont, cellHeader, cellFont, cell, agent);

            Image im2 = Image.getInstance("PieChart.jpeg");
            Image im3 = Image.getInstance("PieChart2.jpeg");
            PdfPTable lol = createTable(2, 110, new float[] {1, 1});
            PdfPCell cell2 = createCell(Color.WHITE, 0);
            cell2.setBorderColor(Color.WHITE);
            cell2.setImage(im2);
            lol.addCell(cell2);
            cell2.setImage(im3);
            lol.addCell(cell2);
            document.add(lol);

            writeRealEstateStats(document, paragraphFont, cellHeaderFont, cellHeader, cellFont, cell, agent);
            
            Image im4 = Image.getInstance("BarChart.jpeg");
            im4.setAlignment(Element.ALIGN_CENTER);
            im4.scalePercent(80);
            document.add(im4);

            response.setHeader("Success", "PDF esportato correttamente!"); 
            log.info("PDF esportato correttamente!");
        } 
        catch (DocumentException | IOException e) 
        {
            log.error("Errore durante l'esportazione del PDF!\n" + e.getMessage());
            throw e;
        }
        finally
        {
            document.close();
        }
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

    private void writeInTable(PdfPTable table, PdfPCell cell, Font cellFont, String ... headers) 
    {
        for(String header : headers)
        {
            cell.setPhrase(new Phrase(header, cellFont));
            table.addCell(cell);
        }
    } 

    private void writeAgentInfo(Document document, Font paragraphFont, Font cellHeaderFont, PdfPCell cellHeader, 
                                Font cellFont, PdfPCell cell, Agent agent) 
    {
        Paragraph agentInfoParagraph = createParagraph(paragraphFont, "AGENT INFO");
        
        PdfPTable agentInfoTable = createTable(3, 80f, new float[] {2.5f, 2.5f, 2.5f});
        String[] agentInfoHeader = {"Name", "Surname", "Username"};
        writeInTable(agentInfoTable, cellHeader, cellHeaderFont, agentInfoHeader);
        String[] agentInfo = {agent.getName(), agent.getSurname(), agent.getUsername()};
        writeInTable(agentInfoTable, cell, cellFont, agentInfo);
        
        document.add(agentInfoParagraph);
        document.add(agentInfoTable);
    }

    private void writeAgentStats(Document document, Font paragraphFont, Font cellHeaderFont, PdfPCell cellHeader, 
                                 Font cellFont, PdfPCell cell, Agent agent) 
    {
        Paragraph p2 = createParagraph(paragraphFont, "AGENT STATS");
        PdfPTable table2 = createTable(5, 100f, new float[] {2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        String[] agentStatsHeader = {"Uploaded Real Estates", "Sold Real Estates", "Rented Real Estates", "Sales Income", "Rentals Income" };
        writeInTable(table2, cellHeader, cellHeaderFont, agentStatsHeader);
        AgentStats agentStats = agent.getAgentStats();
        String[] stats = {((Integer)agentStats.getTotalUploadedRealEstates()).toString(), 
                               ((Integer)agentStats.getTotalSoldRealEstates()).toString(),
                               ((Integer)agentStats.getTotalRentedRealEstates()).toString(),
                               ((Double)agentStats.getSalesIncome()).toString(),
                               ((Double)agentStats.getRentalsIncome()).toString()};
        writeInTable(table2, cell, cellFont, stats);

        document.add(p2);
        document.add(table2);
    }

    private void writeRealEstateStats(Document document, Font paragraphFont, Font cellHeaderFont, PdfPCell cellHeader, 
                                      Font cellFont, PdfPCell cell, Agent agent) 
    {
        Paragraph p3 = createParagraph(paragraphFont, "REAL ESTATES STATS");
        PdfPTable table3 = createTable(5, 105f, new float[] {3.0f, 2.5f, 2.0f, 2.0f, 2.0f});
        String[] realEstateStatsHeader = {"Title", "Uploading Date", "Views Number", "Offers Number", "Visits Number"};
        writeInTable(table3, cellHeader, cellHeaderFont, realEstateStatsHeader);
        List<RealEstate> realEstates = agent.getRealEstates();

        if(realEstates.size() > 0)
        {
            for(RealEstate realEstate : realEstates)
            {
                RealEstateStats realEstateStats = realEstate.getRealEstateStats();
                String[] estateStats = {realEstate.getTitle(),
                                        realEstate.getUploadingDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                                        ((Long)realEstateStats.getViewsNumber()).toString(),
                                        ((Long)realEstateStats.getOffersNumber()).toString(),
                                        ((Long)realEstateStats.getVisitsNumber()).toString()};
        
                writeInTable(table3, cell, cellFont, estateStats);
            }
        }
        else
            writeInTable(table3, cell, cellFont, new String[] {"//", "//", "//", "//", "//"});

        document.add(p3);
        document.add(table3);
    }




/* 
    @Override
    public void onStartPage(PdfWriter writer, Document document) {

        // step 2: create a header
        PdfPTable table = new PdfPTable(3);
        table.setSpacingAfter(32);
        table.setTotalWidth(510);
        table.setWidths(new int[]{38, 36, 36});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(Rectangle.BOTTOM);

        PdfPCell emptyCell = new PdfPCell(new Paragraph(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(emptyCell);
        Paragraph title =  new Paragraph("Header", new Font(Font.COURIER, 20, Font.BOLD));
        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setPaddingBottom(10);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(titleCell);
        table.addCell(emptyCell);

        Font cellFont = new Font(Font.HELVETICA, 8);
        table.addCell(new Paragraph("Phone Number: 888-999-0000", cellFont));
        table.addCell(new Paragraph("Address : 333, Manhattan, New York", cellFont));
        table.addCell(new Paragraph("Website : http://grogu-yoda.com", cellFont));

        //table.writeSelectedRows(0, -1, 34, 828, writer.getDirectContent());

        document.add(table);
    } */

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        // step 3: create a footer
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(520);
        table.setWidths(new int[]{50, 50});

        Paragraph title =  new Paragraph("Exporting date: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE), new Font(Font.HELVETICA, 10));
        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setPaddingTop(10);
        titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        titleCell.setBorderColor(Color.GRAY);
        titleCell.setBorder(Rectangle.TOP);
        table.addCell(titleCell);

        Paragraph pageNumberText =  new Paragraph("Page " + document.getPageNumber(), new Font(Font.HELVETICA, 10));
        PdfPCell pageNumberCell = new PdfPCell(pageNumberText);
        pageNumberCell.setPaddingTop(10);
        pageNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pageNumberCell.setBorderColor(Color.GRAY);
        pageNumberCell.setBorder(Rectangle.TOP);
        //pageNumberCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(pageNumberCell);

        table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
    }
}