package com.leqee.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println();
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
