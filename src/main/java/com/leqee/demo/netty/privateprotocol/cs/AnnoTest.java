package com.leqee.demo.netty.privateprotocol.cs;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class AnnoTest {

    @Anno
    public void test(){}

    public static void main(String[] args) throws NoSuchMethodException {
        int a=1;
        int b = a<<1;
        System.out.println(a);
        Method method = AnnoTest.class.getMethod("test");
        Method method2 = AnnoTest2.class.getMethod("test");
        System.out.println(method.isAnnotationPresent(Anno.class));
        System.out.println(method2.isAnnotationPresent(Anno.class));
    }

    public static class AnnoTest2 extends AnnoTest{
        @Override
        public void test() {
            super.test();
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface Anno{

    }

}
