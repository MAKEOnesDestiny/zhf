package com.leqee.demo;

public class ExceptionLogTest {

    public static void main(String[] args) {
        try {
            throwException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("---------------------------");
            System.out.println(e.getMessage());
            System.out.println("---------------------------");
            System.out.println(e.getStackTrace());
            System.out.println("---------------------------");
            System.out.println(e.toString());
            System.out.println("---------------------------");
            System.out.println(e);
            System.out.println("---------------------------");
        }
    }

    public static void throwException() throws Exception{
        throw new RuntimeException("我是异常信息");
    }

}
