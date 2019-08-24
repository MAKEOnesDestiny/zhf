package com.leqee.demo.controller;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(111);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        Map<String,Object> map = new HashMap();
        for (Map.Entry entry:map.entrySet()) {

        }
    }



}
