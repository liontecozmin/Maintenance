package com.tfo.maintenance.excel;


import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.tfo.maintenance.entity.Alerts;
import com.tfo.maintenance.table.CountryMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFormatter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Collection<Alerts> alertsList;

    public ExcelFormatter(Collection<Alerts> alertsList) {
        this.alertsList = alertsList;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Alert");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "AlertId", style);
        createCell(row, 1, "Status", style);
        createCell(row, 2, "AlertType", style);
        createCell(row, 3, "AlertDate", style);
        createCell(row, 4, "WorkItemId", style);
        createCell(row, 5, "TenantId", style);
        createCell(row, 6, "BranchId", style);
        createCell(row, 7, "EventId", style);
        createCell(row, 8, "RuleId", style);
        createCell(row, 9, "IMO", style);
        createCell(row, 10, "VesselName", style);
        createCell(row, 11, "VesselFlag", style);
        createCell(row, 12, "Owner", style);
        createCell(row, 13, "BeneficiaryOwner", style);
        createCell(row, 14, "VoyageNr", style);
        createCell(row, 15, "DeparturePort", style);
        createCell(row, 16, "DepartureDate", style);
        createCell(row, 17, "ArrivalPort", style);
        createCell(row, 18, "ArrivalDate", style);
        createCell(row, 19, "BrokenRules", style);
        createCell(row, 20, "InsertDateTime", style);
        createCell(row, 21, "UpdateDateTime", style);
        createCell(row, 22, "Country", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Alerts alert : alertsList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, alert.getAlertId(), style);
            createCell(row, columnCount++, alert.getStatus(), style);
            createCell(row, columnCount++, alert.getAlertType(), style);
            createCell(row, columnCount++, alert.getAlertDate().toString(), style);
            createCell(row, columnCount++, alert.getWorkItemId(), style);
            createCell(row, columnCount++, alert.getTenantId(), style);
            createCell(row, columnCount++, alert.getBranchId(), style);
            createCell(row, columnCount++, alert.getEventId(), style);
            createCell(row, columnCount++, alert.getRuleId(), style);
            createCell(row, columnCount++, alert.getImo(), style);
            createCell(row, columnCount++, alert.getVesselName(), style);
            createCell(row, columnCount++, alert.getVesselFlag(), style);
            createCell(row, columnCount++, alert.getOwner(), style);
            createCell(row, columnCount++, alert.getBeneficiaryOwner(), style);
            createCell(row, columnCount++, alert.getVoyageNr(), style);
            createCell(row, columnCount++, alert.getDeparturePort(), style);
            createCell(row, columnCount++, alert.getDepartureDate().toString(), style);
            createCell(row, columnCount++, alert.getArrivalPort(), style);
            createCell(row, columnCount++, alert.getArrivalDate().toString(), style);
            createCell(row, columnCount++, alert.getBrokenRules(), style);
            createCell(row, columnCount++, alert.getInsertDateTime().toString(), style);
            createCell(row, columnCount++, alert.getUpdateDateTime().toString(), style);
            createCell(row, columnCount++, CountryMapper.getCountry(alert.getTenantId()), style);
            System.out.println("date is "+alert.getAlertDate());
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}