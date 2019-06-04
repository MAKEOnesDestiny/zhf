package com.leqee.demo.httpxml.netty;

import com.leqee.demo.httpxml.Order;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;

public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<FullHttpResponse> {
    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
        Object result = decode0(ctx, msg.content());
        HttpXmlResponse response = new HttpXmlResponse(msg, result);
        out.add(response);
    }

    @Override
    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception {
        byte[] data = new byte[body.capacity()];
        body.getBytes(0, data);
        System.out.println("模拟解码工具");
        System.out.println("收到请求消息body:" + new String(data));
        Order order = new Order();
        order.setOrderNumber(321L);
        order.setTotal(654321.0f);
        return order;
    }

}
