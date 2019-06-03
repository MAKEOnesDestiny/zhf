package com.leqee.demo.excel.bean;


import com.leqee.demo.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class DataDictionary {

    private Integer id;
    @Excel(header = "表名")
    private String tableName;
    @Excel(header = "中文表名")
    private String tableNameInChinese;
    @Excel(header = "字段名")
    private String columnName;
    @Excel(header = "中文字段名")
    private String columnNameInChinese;
    @Excel(header = "数据来源")
    private String dataSource;
    //应用模块
    @Excel(header = "应用模块")
    private String applicationModule;
    //数据库名称
    @Excel(header = "数据库")
    private String tableSchema;
    @Excel(header = "业务指标定义")
    private String businessIndicatorDef;
    @Excel(header = "指标类型")
    private String indicatorType;
    @Excel(header = "数据类型")
    private String dataType;
    @Excel(header = "产品对接人")
    private String productDocker;

    private Date updateTime;

}
