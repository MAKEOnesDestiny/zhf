package com.leqee.demo.leqee.jobcenter.http;

import com.leqee.demo.http.HttpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class JobCenterHttpServer {

    public static void main(String[] args) {
        HttpServer hs = new HttpServer("127.0.0.1", 9999);
        try {
            hs.start(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //
                    ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                    //将对象组装为FullHttpRequest
                    ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                    ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                    ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                    ch.pipeline().addLast(new JobCenterUrlHandler());
                }
            });
        } catch (InterruptedException e) {
            hs.close();
        }
    }



}
