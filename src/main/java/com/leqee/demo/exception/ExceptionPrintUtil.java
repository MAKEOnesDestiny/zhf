package com.leqee.demo.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class ExceptionPrintUtil {

    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    public static void main(String[] args) {
        try{
            throw new RuntimeException("Test Exception");
        }catch (RuntimeException e){
            System.out.println(ExceptionPrintUtil.printStackTraceToString(e));
        }
    }

}
