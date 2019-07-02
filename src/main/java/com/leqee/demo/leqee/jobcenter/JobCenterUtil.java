package com.leqee.demo.leqee.jobcenter;

import com.leqee.demo.leqee.jobcenter.bean.JobCenterUrlBean;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public abstract class JobCenterUtil {

    public static String getJobCenterURL(JobCenterUrlBean jcb) {
        StringBuilder sb = new StringBuilder();
        sb.append(jcb.getPureUrl());
        Map<String, Object> para = jcb.getParameters();
        boolean isFirst = true;
        if (!CollectionUtils.isEmpty(para)) {
            for (Map.Entry e : para.entrySet()) {
                if (isFirst) {
                    sb.append("?");
                    isFirst = false;
                } else {
                    sb.append("&");
                }
                sb.append(e.getKey()).append("=").append(e.getValue());
            }
        }
        return sb.toString();
    }

    public static JobCenterUrlBean splitJobCenterURL(String url, JobCenterUrlBean jcb) {
        if (StringUtils.isEmpty(url)) {
            log.info("Input String is null or empty");
            throw new RuntimeException("Input String is null or empty");
        }
//        JobCenterUrlBean jcb = new JobCenterUrlBean(url);
        String[] sUrl = url.split("\\?");
        if (sUrl.length != 2) {
            log.info("{}: has no parameters or has no ?", url);
            jcb.setPureUrl(sUrl[0]);
            return jcb;
        }
        jcb.setPureUrl(sUrl[0]);
        //todo: skip if no parameters
        String[] urlParameters = sUrl[1].split("&");
        Map<String, Object> paraMap = new HashMap<>();
        for (String p : urlParameters) {
            if (isParameterExprValid(p)) {
                String[] singlePara = p.split("=");
                //todo: override
                paraMap.put(singlePara[0], singlePara[1].equalsIgnoreCase("null") ? null : singlePara[1]);
            }
            //skip if not valid
        }
        jcb.setParameters(paraMap);
        return jcb;
    }

    public static boolean isParameterExprValid(String pe) {
        if (StringUtils.isEmpty(pe)) {
            log.info("url parameter expr is empty");
            return false;
        }
        String[] peParsed = pe.split("=");
        if (peParsed.length == 2 && !StringUtils.isEmpty(peParsed[0]))
            return true;
        else return false;
    }

    public static void main(String[] args) {
        System.out.println();
    }

}
