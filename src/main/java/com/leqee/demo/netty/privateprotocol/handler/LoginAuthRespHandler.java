package com.leqee.demo.netty.privateprotocol.handler;

import com.leqee.demo.netty.privateprotocol.bean.Header;
import com.leqee.demo.netty.privateprotocol.bean.MessageType;
import com.leqee.demo.netty.privateprotocol.bean.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    //登录信息缓存
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();

    private String[] whitekList = {"/127.0.0.1:8000", "/172.22.16.190:8000"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;
        if (nettyMessage.getHeader() != null && nettyMessage.getHeader().getType() == MessageType.LOGIN_REQ.getValue()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            if (nodeCheck.containsKey(nodeIndex)) {
                ctx.writeAndFlush(buildResponse((byte) -1));  //重复登陆
            } else {
                for (String w : whitekList) {
                    if (w.equals(nodeIndex)) {
                        nodeCheck.put(nodeIndex, true);
                        ctx.writeAndFlush(buildResponse((byte) 0));
                        return;
                    }
                }
                ctx.writeAndFlush(buildResponse((byte) -2)); //如果不在白名单内,直接拒绝登录
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.getValue());
        nettyMessage.setHeader(header);
        nettyMessage.setBody(result);
        return nettyMessage;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString()); //删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
