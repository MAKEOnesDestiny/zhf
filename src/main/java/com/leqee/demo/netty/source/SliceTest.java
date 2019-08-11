package com.leqee.demo.netty.source;

import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

public class SliceTest {

    public static void main(String[] args) {
        byte[] a = new byte[]{1, 2, 3, 4, 5};
        ByteBuffer byteBuffer = ByteBuffer.wrap(a);
        ByteBuffer byteBuffer1 = byteBuffer.slice();
        System.out.println();
    }


}
