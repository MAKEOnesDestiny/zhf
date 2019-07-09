package com.leqee.demo.netty.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public class MarshallingCodeCFactory {

    //适配器
    public interface MarshallingFactoryAdapter {
        default MarshallingEncoder getEncoder(MarshallerProvider provider) {
            return null;
        }

        default MarshallingDecoder getDecoder(UnmarshallerProvider provider) {
            return null;
        }
    }

    public static MarshallingDecoder buildMarshingDecoder(MarshallingFactoryAdapter adapter) {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        MarshallingDecoder decoder = adapter.getDecoder(provider);
        return decoder;
    }

    public static MarshallingEncoder buildMarshingEncoder(MarshallingFactoryAdapter adapter) {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        MarshallingEncoder decoder = adapter.getEncoder(provider);
        return decoder;
    }


    public static MarshallingDecoder buildMarshingDecoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
        return decoder;
    }

    public static MarshallingEncoder buildMarshingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        MarshallingEncoder decoder = new MarshallingEncoder(provider);
        return decoder;
    }

    public static Marshaller buildMarshaller(ChannelHandlerContext ctx) throws Exception {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        return provider.getMarshaller(ctx);
    }


}
