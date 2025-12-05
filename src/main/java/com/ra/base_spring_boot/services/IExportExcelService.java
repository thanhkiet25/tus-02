package com.ra.base_spring_boot.services;

import java.util.List;

public interface IExportExcelService {
    byte[] exportExcel(String sheetName, String[] headers, List<Object[]> rows);
}
