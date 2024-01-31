package com.gestion.util.reports;

import com.gestion.model.Employee;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author SamuelBoada
 */
public class EmployeeExporterPdf {

    private List<Employee> listEmployee;

    public EmployeeExporterPdf(List<Employee> listEmployee) {
        this.listEmployee = listEmployee;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Surname", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Salary", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (Employee employee : listEmployee) {
            table.addCell(String.valueOf(employee.getId()));
            table.addCell(employee.getFirstName());
            table.addCell(employee.getLastName());
            table.addCell(employee.getEmail());
            table.addCell(String.valueOf(employee.getPhoneNumber()));
            table.addCell(employee.getGender());
            table.addCell(employee.getDate().toString());
            table.addCell(String.valueOf(employee.getSalary()));
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLUE);
        font.setSize(18);
        Paragraph title = new Paragraph("Employee List", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        table.setWidths(new float[]{1f, 2.3f, 2.3f, 6f, 3.5f, 2f, 2.9f, 2.2f});
        table.setWidthPercentage(110);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }
}
