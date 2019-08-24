package com.leqee.demo.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestBean {

    private Long id;

    private BigDecimal price;

    private int platform;

    private String omsBusinessCode;

    private String omsGoodsName;

    private String platformGoodsCode;

    private String platformGoodsName;

    private boolean deprecated;

}
