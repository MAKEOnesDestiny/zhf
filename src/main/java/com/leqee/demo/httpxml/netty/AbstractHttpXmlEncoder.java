package com.leqee.demo.httpxml.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;

public abstract class AbstractHttpXmlEncoder<I> extends MessageToMessageEncoder<I> {

    StringWriter writer = null;

    protected ByteBuf encode0(ChannelHandlerContext ctx,Object body) throws Exception{
        File file = ResourceUtils.getFile("file:order.xml");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        return Unpooled.copiedBuffer(data);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(writer!=null){
            writer.close();
            writer = null;
        }
    }
}
