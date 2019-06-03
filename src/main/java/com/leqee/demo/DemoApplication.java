package com.leqee.demo;

import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {

    public static void main(String[] args) {
        Object applicationContext = SpringApplication.run(DemoApplication.class, args);
        System.out.println(1);
    }

    public static Map<JdbcType, Class> map = new HashMap<>();

    public static void test1() {

    }

    public static void test2() {
        map.put(JdbcType.BOOLEAN, Boolean.class);
        map.put(JdbcType.TINYINT, Byte.class);
        map.put(JdbcType.SMALLINT, Short.class);
        map.put(JdbcType.INTEGER, Integer.class);
        map.put(JdbcType.INTEGER, Long.class);
        map.put(JdbcType.FLOAT, Float.class);
        map.put(JdbcType.DOUBLE, Double.class);
        map.put(JdbcType.CHAR, String.class);
        map.put(JdbcType.CLOB, String.class);
        map.put(JdbcType.VARCHAR, String.class);
        map.put(JdbcType.LONGVARCHAR, String.class);
        map.put(JdbcType.NVARCHAR, String.class);
        map.put(JdbcType.NCHAR, String.class);
        map.put(JdbcType.NCLOB, String.class);
        map.put(JdbcType.CHAR, String.class);
        map.put(JdbcType.VARCHAR, String.class);
        map.put(JdbcType.BIGINT, Long.class);
        map.put(JdbcType.DECIMAL, BigDecimal.class);
        map.put(JdbcType.REAL, BigDecimal.class);
        map.put(JdbcType.NUMERIC, BigDecimal.class);
        map.put(JdbcType.DATE, Date.class);
        map.put(JdbcType.TIME, Date.class);
        map.put(JdbcType.TIMESTAMP, Date.class);
    }

}
