package com.leqee.demo.httpxml.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {
        ByteBuf result = encode0(ctx, msg.getResult());
        FullHttpResponse response = msg.getResponse();
        if (response == null) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, result);
        } else {
            response = new DefaultFullHttpResponse(msg.getResponse().getProtocolVersion(), msg.getResponse().getStatus(), result);
        }
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/xml;charset=UTF-8");
        HttpHeaders.setContentLength(response, result.readableBytes());
        out.add(response);
    }

    @Override
    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws Exception {
        File file = ResourceUtils.getFile("src/main/java/com/leqee/demo/httpxml/netty/order.xml");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        return Unpooled.copiedBuffer(data);
    }


}
