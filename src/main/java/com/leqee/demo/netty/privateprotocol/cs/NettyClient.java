package com.leqee.demo.netty.privateprotocol.cs;

import com.leqee.demo.netty.privateprotocol.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    static EventLoopGroup group = new NioEventLoopGroup();

    public static void connect(int port, String host) {

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler())
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler());
                            //请求从下到上，响应从上到下
                            ch.pipeline().addLast(new NettyMessageDecoder(65536, 4, 4));
                            ch.pipeline().addLast(new TestEncoder());
                            ch.pipeline().addLast(new NettyMessageEncoder());
//                            ch.pipeline().addLast(new ReadTimeoutHandler(7)); //7秒超时
                            ch.pipeline().addLast(new LoginAuthReqHandler());
                            ch.pipeline().addLast(new HeartBeatReqHandler());
                        }
                    });
            ChannelFuture cf = b.connect(new InetSocketAddress(host, port)
                    , new InetSocketAddress("127.0.0.1", 8000)).sync();
            System.out.println("Client connect to server succeed!");
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {

        } finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        connect(port, host);
                        System.out.println("reconnect to server succeed!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        connect(9000, "127.0.0.1");
    }

}
