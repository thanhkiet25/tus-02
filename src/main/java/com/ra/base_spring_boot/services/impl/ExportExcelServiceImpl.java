package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.services.IExportExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Service
public class ExportExcelServiceImpl implements IExportExcelService {
    @Override
    public byte[] exportExcel(String sheetName, String[] headers, List<Object[]> rows) {
        //Tạo file Excel dạng .xlsx.
        //Dùng để ghi dữ liệu Excel vào mảng byte.
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetName);

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // 1. Header
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 2.Bắt đầu ghi dữ liệu từ dòng thứ 1 (vì dòng 0 là header).
            int rowIdx = 1;
            for (Object[] rowData : rows) {
                Row row = sheet.createRow(rowIdx++);
                for (int i = 0; i < rowData.length; i++) {
                    row.createCell(i).setCellValue(
                            rowData[i] != null ? rowData[i].toString() : ""
                    );
                }
            }

            // 3.Tự động điều chỉnh độ rộng cột cho vừa nội dung.
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // 4.Ghi toàn bộ Workbook vào ByteArrayOutputStream.
            // Trả về byte[] → Controller sẽ cho trình duyệt tải file.
            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error while exporting Excel file: " + e.getMessage());
        }
    }
}
