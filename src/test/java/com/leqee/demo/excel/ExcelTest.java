package com.leqee.demo.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelTest {

    public static void main(String[] args) throws IOException {
        OutputStream os = new FileOutputStream("D:\\newexcel.xls");
        HSSFWorkbook  wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("测试单元格");
//        wb.getSheetAt(0);
        wb.write(os);
        os.flush();
        os.close();
    }

}
