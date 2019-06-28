package com.leqee.demo.utils;

import com.leqee.demo.exception.ExceptionPrintUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

@Log4j2
public abstract class ExcelUtil {

    public static byte[] deleteHeaderRows(byte[] bytes, int sheetIndex, int num) {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
        return deleteHeaderRows(wb, sheetIndex, num);
    }

    public static byte[] deleteHeaderRows(Workbook workbook, int sheetIndex, int num) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int n = num < 0 ? Math.abs(num) : num;
        for (int i = 0; i < n; i++) {
            Row row = sheet.getRow(i);
            if (row != null) sheet.removeRow(row);
        }
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);
            return os.toByteArray();
        } catch (IOException e) {
            log.error(ExceptionPrintUtil.printStackTraceToString(e));
            return null;
        }
    }


    public static byte[] shiftHeaderRows(byte[] bytes, int sheetIndex, int num) {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
        return shiftHeaderRows(wb, sheetIndex, num);
    }

    public static byte[] shiftHeaderRows(Workbook workbook, int sheetIndex, int num) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int n = num < 0 ? Math.abs(num) : num;
        for (int i = 0; i < n; i++) {
            sheet.shiftRows(1, sheet.getLastRowNum(), -1);//删除第一行到第四行，然后使下方单元格上移
        }
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);
            return os.toByteArray();
        } catch (IOException e) {
            log.error(ExceptionPrintUtil.printStackTraceToString(e));
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            FileInputStream is = new FileInputStream("f://test.xls");
            FileInputStream is2 = new FileInputStream("f://test2.xls");
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFWorkbook workbook2 = new HSSFWorkbook(is2);

            Row row = workbook.getSheetAt(0).getRow(5);
            Row row2 = workbook2.getSheetAt(0).createRow(0);
            Cell nCell = row2.createCell(0);

            Cell cell = row.getCell(8);
            cell.setCellType(CellType.STRING);
            CellStyle cellStyle = cell.getCellStyle();
            nCell.setCellValue(cell.getStringCellValue());

            CellStyle cellStyle2 = workbook2.createCellStyle();
            cellStyle2.cloneStyleFrom(cellStyle);
            nCell.setCellStyle(cellStyle2);

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
