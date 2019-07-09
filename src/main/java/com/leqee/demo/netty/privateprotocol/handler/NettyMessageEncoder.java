package com.leqee.demo.netty.privateprotocol.handler;

import com.leqee.demo.netty.marshalling.MarshallingCodeCFactory;
import com.leqee.demo.netty.privateprotocol.bean.NettyMessage;
import com.leqee.demo.netty.privateprotocol.handler.marshalling.MarshallingEncoderExt;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.util.List;
import java.util.Map;

public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

//    MessagePack messagePack = new MessagePack();

    MarshallingEncoderExt marshallingEncoder;

    public NettyMessageEncoder() {
        this.marshallingEncoder = (MarshallingEncoderExt) MarshallingCodeCFactory
                .buildMarshingEncoder(new MarshallingCodeCFactory.MarshallingFactoryAdapter() {
                    @Override
                    public MarshallingEncoder getEncoder(MarshallerProvider provider) {
                        return new MarshallingEncoderExt(provider);
                    }
                });
    }

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
            //attachment
            key = e.getKey();
            keyArray = key.getBytes("UTF-8");
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            value = e.getValue();
//            byte[] keyValue = messagePack.write(value);
            marshallingEncoder.encode(ctx, value, sendBuf);
//            sendBuf.writeInt(keyValue.length);
//            sendBuf.writeBytes(keyValue);
        }
        key = null;
        keyArray = null;
        value = null;
        if (msg.getBody() != null) {
//            sendBuf.writeBytes(messagePack.write(msg.getBody()));
            marshallingEncoder.encode(ctx, msg.getBody(), sendBuf);
        } else {
            sendBuf.writeInt(0); //
        }
        //这里设置NettyMessage中Header的length字段
        sendBuf.setInt(4, sendBuf.readableBytes() - 8);
        out.add(sendBuf);
    }
}
