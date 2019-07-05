package com.leqee.demo.controller;

import com.leqee.demo.excel.ExcelUtil;
import com.leqee.demo.excel.bean.DataDictionary;
import com.sun.org.apache.xerces.internal.xs.XSFacet;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
public class ExcelController {

    String[] beanSetterMethod = {"setId", "setTableName", "setTableNameInChinese", "setColumnName", "setColumnNameInChinese", "setDataSource"
            , "setApplicationModule", "setTableSchema", "setBusinessIndicatorDef", "setIndicatorType", "setDataType", "setProductDocker"};

    Class[] beanFieldClass = {Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
            , String.class, String.class, String.class, String.class};

    @RequestMapping(value = "/get/file")
    public void getFile(@RequestPart(name = "file") MultipartFile file) throws IOException {
        List<DataDictionary> list = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        int firstNum = sheet.getFirstRowNum();
        int lastNum = sheet.getLastRowNum();
        for (int i = firstNum + 1; i < lastNum + 1; i++) {
            Row row = sheet.getRow(i);
            short firstCellNum = row.getFirstCellNum();
            short lastCellNum = row.getLastCellNum();
            DataDictionary dataDictionary = new DataDictionary();
            for (int j = firstCellNum; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(DataDictionary.class, beanSetterMethod[j], beanFieldClass[j]), dataDictionary, j == 0 ? Integer.valueOf((int) cell.getNumericCellValue()) : cell.getStringCellValue());
            }
            list.add(dataDictionary);
        }
        System.out.println(list);
    }


    @RequestMapping(value = "/get/excel")
    public void getExcel(HttpServletResponse response) {
    /*    HSSFWorkbook hssfWorkbook = ExcelUtil.getHSSFWorkbook();
        try {
            hssfWorkbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
