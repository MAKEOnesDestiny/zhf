package com.leqee.demo.httpxml.netty;

import com.leqee.demo.httpxml.Order;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;

public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {
        if (!msg.getDecoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            System.out.println("解码失败，返回BAD REQUEST错误");
        }
        Object obj = decode0(ctx, msg.content());
        HttpXmlRequest request = new HttpXmlRequest(msg, obj);
        out.add(request);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status
                , Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception {
        byte[] data = new byte[body.capacity()];
        body.getBytes(0, data);
        System.out.println("模拟解码工具");
        System.out.println("收到请求消息body:" + new String(data));
        Order order = new Order();
        order.setOrderNumber(123L);
        order.setTotal(123456.0f);
        return order;
    }

}
