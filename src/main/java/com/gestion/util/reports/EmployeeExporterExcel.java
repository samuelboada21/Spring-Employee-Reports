package com.gestion.util.reports;

import com.gestion.model.Employee;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author SamuelBoada
 */
public class EmployeeExporterExcel {

    private XSSFWorkbook book;
    private XSSFSheet sheet;

    private List<Employee> listEmployee;

    public EmployeeExporterExcel(List<Employee> listEmployee) {
        this.listEmployee = listEmployee;
        book = new XSSFWorkbook();
        sheet = book.createSheet("Employees");
    }

    private void writeTableHeader() {
        Row row = sheet.createRow(0);

        CellStyle style = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Surname");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Email");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Phone");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Gender");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Date");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("Salary");
        cell.setCellStyle(style);
    }

    private void writeTableData() {
        int numRow = 1;

        CellStyle style = book.createCellStyle();
        XSSFFont font = book.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Employee employee : listEmployee) {
            Row row = sheet.createRow(numRow++);

            Cell cell = row.createCell(0);
            cell.setCellValue(employee.getId());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(employee.getFirstName());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(employee.getLastName());
            sheet.autoSizeColumn(2);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(employee.getEmail());
            sheet.autoSizeColumn(3);
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(employee.getPhoneNumber());
            sheet.autoSizeColumn(4);
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(employee.getGender());
            sheet.autoSizeColumn(5);
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(employee.getDate());
            sheet.autoSizeColumn(6);
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(employee.getSalary());
            sheet.autoSizeColumn(7);
            cell.setCellStyle(style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeTableHeader();
        writeTableData();

        ServletOutputStream outPutStream = response.getOutputStream();
        book.write(outPutStream);
        
        book.close();
        outPutStream.close();
    }
}
