package com.leqee.demo.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HttpServer {

    private String host = "127.0.0.1";

    private Integer port;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private volatile boolean started = false;

    private ChannelFuture cf;

    public HttpServer(String host, Integer port, EventLoopGroup bossGroup, EventLoopGroup workGroup) {
        this.host = host;
        this.port = port;
        this.bossGroup = bossGroup;
        this.workGroup = workGroup;
    }

    public HttpServer(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.bossGroup = new NioEventLoopGroup();
        this.workGroup = new NioEventLoopGroup();
    }

    public ChannelFuture start(ChannelHandler ch) throws InterruptedException {
        if (started) {
            return cf.sync();
        }
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .childHandler(ch);
        ChannelFuture cf = b.bind(host, port).sync();
        this.cf = cf;
        started = true;
        log.debug("HttpServer started at {}:{}", host, port);
        return cf;
    }

    public void close() {
        try {
            if (!started) {
                return;
            }
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            log.debug("HttpServer closed at {}:{}", host, port);
        }
    }

}
