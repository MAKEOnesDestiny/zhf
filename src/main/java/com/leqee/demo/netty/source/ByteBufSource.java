package com.leqee.demo.netty.source;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufSource {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(1);
        buf.writeInt(1);
    }

}
