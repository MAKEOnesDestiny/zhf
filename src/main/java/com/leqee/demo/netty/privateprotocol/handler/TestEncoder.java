package com.leqee.demo.netty.privateprotocol.handler;

import com.leqee.demo.netty.privateprotocol.bean.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class TestEncoder  extends MessageToMessageEncoder<NettyMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        System.out.println("test encoder执行");
    }
}
