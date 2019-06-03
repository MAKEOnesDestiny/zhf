package com.leqee.demo.httpxml.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.net.InetAddress;
import java.util.List;

public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, msg);
        FullHttpRequest request = msg.getRequest();
        if (request == null) {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaders.Names.HOST, InetAddress.getLocalHost());
            headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            headers.set(HttpHeaders.Names.ACCEPT, "text/html,application/xhtml+xml,application/xml;");
        }
        HttpHeaders.setContentLength(request, body.readableBytes());
        out.add(request); //for aggregation
    }
}
