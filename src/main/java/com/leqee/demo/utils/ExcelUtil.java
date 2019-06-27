package com.leqee.demo.utils;

import com.leqee.demo.exception.ExceptionPrintUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            System.out.println(workbook.getSheetAt(0).getLastRowNum());

            byte[] b = deleteHeaderRows(workbook, 0, 3);

            HSSFWorkbook workbook1 = new HSSFWorkbook(new ByteArrayInputStream(b));
            System.out.println(workbook1.getSheetAt(0).getLastRowNum());

//            FileOutputStream os = new FileOutputStream("d://test.xls");
//            workbook.write(os);
//            is.close();
//            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
