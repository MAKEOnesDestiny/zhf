package com.leqee.demo.leqee.jobcenter.bean;

import com.leqee.demo.leqee.jobcenter.JobCenterUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Data
@Log4j2
public class JobCenterUrlBean implements CommonParameter {

    private String rawUrl;

    private String pureUrl;

    private Map<String, Object> parameters;

    private UrlType urlType;

    private volatile boolean isInit = false;

    public JobCenterUrlBean(String url) {
        rawUrl = url;
        try {
            JobCenterUtil.splitJobCenterURL(rawUrl, this);
            isInit = true;
        }catch (RuntimeException e){
        }
    }

    public void setPureUrl(String pureUrl) {
        this.pureUrl = pureUrl;
        if (pureUrl != null) {
            this.isInit = true;
        }
    }

    public void unInitCheck() {
        if (!isInit()) {
            log.error(this.toString() + " is not initialized");
            throw new RuntimeException(this.toString() + " is not initialized");
        }
    }

    @Override
    public String getJobCode() {
        unInitCheck();
        return stringValueOf(getParameters().get("jobCode"));
    }

    @Override
    public String getType() {
        unInitCheck();
        return stringValueOf(getParameters().get("type"));
    }


    @Override
    public String getSqlKey() {
        unInitCheck();
        return stringValueOf(getParameters().get("sqlKey"));
    }

    @Override
    public String getEnable() {
        unInitCheck();
        return stringValueOf(getParameters().get("key"));
    }

    @Override
    public String getSheetIndex() {
        unInitCheck();
        return stringValueOf(getParameters().get("sheetIndex"));
    }

    public static String stringValueOf(Object o) {
        return o == null ? null : o.toString();
    }

}
