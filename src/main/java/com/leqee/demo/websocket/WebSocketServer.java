package com.leqee.demo.websocket;

import com.leqee.demo.exception.ExceptionPrintUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class WebSocketServer {

    public static void main(String[] args) {
        //用于接收请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于处理请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpServerCodec()); //将请求和响应解码或编码为HTTP消息
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            ch.pipeline().addLast(new ChunkedWriteHandler()); //向客户端发送数据
                            ch.pipeline().addLast(new WebSocketServerHandler());//自定义WebSocket处理类
                        }
                    });
            Channel channel = b.bind(7777).sync().channel();
            log.debug("WebSocket server start successfully on 7777");
            channel.closeFuture().sync();
            log.debug("WebSocket server close successfully");
        } catch (InterruptedException e) {
            System.out.println(ExceptionPrintUtil.printStackTraceToString(e));
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
