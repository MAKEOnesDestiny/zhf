package com.leqee.demo.netty.source;

public class ArrayCopyTest {

    public static void main(String[] args) {
        /*int[] dst = new int[]{1, 2, 3, 4, 5, 6};
        int[] src = new int[]{7, 7, 7,7,7};
        for (int i:dst) {
            System.out.println(i);
        }
        System.out.println("-----------------------");
        System.arraycopy(src, 0, dst, 0, 5);
        for (int i:dst) {
            System.out.println(i);
        }*/
        Bean b1 = new Bean();
        Bean b2 = new Bean();
        Bean b3 = new Bean();
        Bean b4 = new Bean();
        System.out.println("B1:" + b1.hashCode());
        System.out.println("B4:" + b4.hashCode());
        Bean[] dst = new Bean[]{b1, b2, b3};
        Bean[] src = new Bean[]{b4};
        System.arraycopy(src,0,dst,0,1);
        System.out.println("dst[0]:"+dst[0].hashCode());

    }

    public static class Bean {
        public int i;
    }

}
