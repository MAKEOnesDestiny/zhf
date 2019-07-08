package com.leqee.demo.netty.privateprotocol.handler;

import com.leqee.demo.netty.privateprotocol.bean.Header;
import com.leqee.demo.netty.privateprotocol.bean.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.msgpack.MessagePack;

import java.util.HashMap;
import java.util.Map;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private MessagePack messagePack = new MessagePack();

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(in.readInt());
        header.setLength(in.readInt());
        header.setSessionId(in.readLong());
        header.setType(in.readByte());
        header.setPriority(in.readByte());

        int size = in.readInt();
        if (size > 0) {
            //attachment
            Map<String, Object> attch = new HashMap<>();
            int keySize = 0;
            byte[] array = null;
            String key = null;
            Object value = null;
            for (int i = 0; i < size; i++) {
                keySize = in.readInt();
                array = new byte[keySize];
                in.readBytes(array);
                key = new String(array, "UTF-8");

                keySize = in.readInt();
                array = new byte[keySize];
                in.readBytes(array);
                value = messagePack.read(array);
                attch.put(key, value);
            }
            header.setAttachment(attch);
        }

        if (in.readableBytes() > 4) {
            message.setBody(messagePack.read(in.array()));
        }
        message.setHeader(header);
        return message;
    }
}
