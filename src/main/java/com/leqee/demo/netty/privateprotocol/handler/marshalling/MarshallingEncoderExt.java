package com.leqee.demo.netty.privateprotocol.handler.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

public class MarshallingEncoderExt extends MarshallingEncoder {
    /**
     * Creates a new encoder.
     *
     * @param provider the {@link MarshallerProvider} to use
     */
    public MarshallingEncoderExt(MarshallerProvider provider) {
        super(provider);
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        super.encode(ctx, msg, out);
    }

}
