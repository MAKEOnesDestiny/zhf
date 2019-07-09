package com.leqee.demo.netty.privateprotocol.handler;

import com.leqee.demo.netty.marshalling.MarshallingCodeCFactory;
import com.leqee.demo.netty.privateprotocol.bean.Header;
import com.leqee.demo.netty.privateprotocol.bean.NettyMessage;
import com.leqee.demo.netty.privateprotocol.handler.marshalling.MarshallingDecoderExt;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * LengthFieldBasedFrameDecoder解码器支持自动的TCP粘包和半包的处理
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

//    private MessagePack messagePack = new MessagePack();

    MarshallingDecoderExt marshallingDecoderExt;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingDecoderExt = (MarshallingDecoderExt) MarshallingCodeCFactory
                .buildMarshingDecoder(new MarshallingCodeCFactory.MarshallingFactoryAdapter() {
                    @Override
                    public MarshallingDecoder getDecoder(UnmarshallerProvider provider) {
                        return new MarshallingDecoderExt(provider);
                    }
                });
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionId(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());

        int size = frame.readInt();
        if (size > 0) {
            //attachment
            Map<String, Object> attch = new HashMap<>();
            int keySize = 0;
            byte[] array = null;
            String key = null;
            Object value = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                array = new byte[keySize];
                frame.readBytes(array);
                key = new String(array, "UTF-8");

                keySize = frame.readInt();
                array = new byte[keySize];
                frame.readBytes(array);
//                value = messagePack.read(array);
                marshallingDecoderExt.decode(ctx, frame);
                attch.put(key, value);
            }
            header.setAttachment(attch);
        }

        if (frame.readableBytes() > 4) {
//            message.setBody(messagePack.read(in.array()));
            message.setBody(marshallingDecoderExt.decode(ctx, frame));
        }
        message.setHeader(header);
        return message;
    }
}
