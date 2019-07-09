package com.leqee.demo.netty.privateprotocol.handler;

import com.leqee.demo.netty.privateprotocol.bean.Header;
import com.leqee.demo.netty.privateprotocol.bean.MessageType;
import com.leqee.demo.netty.privateprotocol.bean.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

//服务端心跳handler
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEART_BEAT_REQ.getValue()) {
            System.out.println("[" + new Date() + "]Server receive heart beat request from client:---->" + message);
            NettyMessage nettyMessage = buildHeartBeatResp();
            System.out.println("[" + new Date() + "]Server send heart beat response to client:---->" + nettyMessage);
            ctx.writeAndFlush(nettyMessage);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeartBeatResp() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEART_BEAT_RESP.getValue());

        message.setHeader(header);
        return message;
    }

}
