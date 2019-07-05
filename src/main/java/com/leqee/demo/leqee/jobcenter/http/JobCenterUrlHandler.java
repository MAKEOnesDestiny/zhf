package com.leqee.demo.leqee.jobcenter.http;

import com.alibaba.fastjson.JSONObject;
import com.leqee.demo.exception.ExceptionPrintUtil;
import com.leqee.demo.leqee.jobcenter.bean.JobCenterUrlBean;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.Charsets;

@Log4j2
public class JobCenterUrlHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if (msg.getDecoderResult().isSuccess()) {
            String content = msg.content().toString(Charsets.toCharset("UTF-8"));
            JobCenterUrlBean jcb = new JobCenterUrlBean(content);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK
                    , Unpooled.copiedBuffer(JSONObject.toJSONString(jcb), CharsetUtil.UTF_8));
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json;charset=UTF-8");
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void generateUrl(){

    }

    private void parseUrl(){
        
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(ExceptionPrintUtil.printStackTraceToString(cause));
        sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status
                , Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
