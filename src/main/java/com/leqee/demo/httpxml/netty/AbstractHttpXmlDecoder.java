package com.leqee.demo.httpxml.netty;

import com.leqee.demo.httpxml.Order;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class AbstractHttpXmlDecoder<I> extends MessageToMessageDecoder<I> {

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception {
        System.out.println("模拟解码工具");
        System.out.println("收到请求消息body:" + body);
        Order order = new Order();
        order.setOrderNumber(123L);
        order.setTotal(123456.0f);
        return order;
    }

}
