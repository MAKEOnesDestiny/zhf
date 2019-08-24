package com.leqee.demo.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

    public static void main(String[] args) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

        System.out.println(startTime);
        System.out.println(endTime);
    }

}
