package com.leqee.demo.httpxml.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class HttpXmlServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); //用于接收请求
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //用于reactor模式
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //handler的编号从上到下为：1,2,3.....,N
                        //外部请求从下到上经过handler,即从N>>N-1>>....>>1
                        //内部响应从上到下经过handler,即从1>>2>>....>>N
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpRequestDecoder());//解码过后可能会拆分成多个请求
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            ch.pipeline().addLast(new HttpXmlRequestDecoder());
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new HttpXmlResponseEncoder());
                            ch.pipeline().addLast(new HttpXmlServerHandler());
                        }
                    });
            ChannelFuture future = sb.bind(8080).sync();
            System.out.println("Netty Server启动成功");
            future.channel().closeFuture().sync();
            System.out.println("Netty Server成功关闭");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("bossGroup and workerGroup shutdown gracefully~");
        }
    }

    public static class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, HttpXmlRequest msg) throws Exception {
            System.out.println("Netty Server接收到消息:" + msg.getBody());
            ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse());
            if (!msg.getRequest().headers().get(HttpHeaders.Names.CONNECTION).equalsIgnoreCase("keep-alive")) {
                future.addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        ctx.close();
                        System.out.println("Netty server ctx关闭");
                    }
                });
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            if (ctx.channel().isActive()) {
                sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
            }
            System.out.println("Netty server ctx异常关闭");
        }

        private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status
                    , Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

    }


}
