package com.leqee.demo;

import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) {
        String a = "1,2,3,4,";
        List list = CollectionUtils.arrayToList(a.split(","));
        System.out.println(1);
    }

    public static void do1() {
        String s = "";
        s = s.replaceAll("\t", "\",\r\n\"");
        System.out.println(s);
    }

    public static void do2() {
        try {
            String s = "";
            s = s.replaceAll("\t", "\",\r\n\"");
            System.out.println(s);
            InputStream is = new FileInputStream("D://");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println(1);
        }

    }


}
