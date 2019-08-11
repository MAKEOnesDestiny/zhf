package com.leqee.demo.netty;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Random;
import java.util.concurrent.FutureTask;

public class Test {

    public String test;

    public int test1;

    public void test() {
        Inner inner = new Inner();
        inner.inn = "b";
        System.out.println(inner.inn);
        System.out.println(new Test().new Inner().inn);
        String a = null;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(a);
            }
        };
        System.out.println();
    }

    protected FutureTask test1(){
        return null;
    }

    public class Inner {
        private String inn = "aaa";

        public void test() {
            System.out.println(test);
        }
    }

    public static class TestInner extends Test{
        @Override
         public FutureTask test1() {
            return null;
        }
    }

    //1564052213060
    //16732 80600 000
    public static void main(String[] args) throws ParseException, NoSuchFieldException {
//        System.out.println(UUID.randomUUID().toString());
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = sdf.parse("2023-10-10");
        System.out.println(date.getTime());*/
        Test t = new Test();
        t.test();
        t.new Inner().test();
        Field f = Test.class.getField("test1");
        ReflectionUtils.setField(f, t, null);

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            System.out.println(random.nextInt(100));
        }
    }

}
