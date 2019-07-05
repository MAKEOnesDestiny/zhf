package com.leqee.demo.netty.privateprotocol.handler;

import com.leqee.demo.netty.privateprotocol.bean.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.msgpack.MessagePack;

import java.util.List;
import java.util.Map;

public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    //    MarshallingEncoder marshallingEncoder = new MarshallingEncoder();
    MessagePack messagePack = new MessagePack();

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List out) throws Exception {
        if (msg == null || msg.getHeader() == null) throw new Exception("The encode message is null");
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode()); //4个字节的offset
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeLong(msg.getHeader().getSessionId());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());
        sendBuf.writeInt(msg.getHeader().getAttachment().size());
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> e : msg.getHeader().getAttachment().entrySet()) {
            key = e.getKey();
            keyArray = key.getBytes("UTF-8");
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            value = e.getValue();
            sendBuf.writeBytes(messagePack.write(value));
        }
        key = null;
        keyArray = null;
        value = null;
        if (msg.getBody() != null) {
            sendBuf.writeBytes(messagePack.write(msg.getBody()));
        } else {
            sendBuf.writeInt(0);
        }
        sendBuf.setInt(4, sendBuf.readableBytes());
    }
}