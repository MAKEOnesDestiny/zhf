package com.leqee.demo.httpxml.netty;

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
}
