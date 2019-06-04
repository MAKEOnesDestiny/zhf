package com.leqee.demo.httpxml.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.InetSocketAddress;

public class HtmlXmlClient {

    public static void main(String[] args) {
        try {
            new HtmlXmlClient().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        //请求从下到上经过handler,响应从上到下经过handler
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpResponseDecoder());
                            //将一个HTTP请求消息的多个部分合并成为一个完整的HTTP请求
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            //xml解码器
                            ch.pipeline().addLast("xml-resp-decoder", new HttpXmlResponseDecoder());
                            ch.pipeline().addLast("http-encoder", new HttpRequestEncoder());
                            ch.pipeline().addLast("xml-req-encoder", new HttpXmlRequestEncoder());
//                            ch.pipeline().addLast("xmlClientHandler", new HttpXmlRequestEncoder());
                            ch.pipeline().addLast("XmlClientHandler",new HttpXmlClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(new InetSocketAddress(8080)).sync();
            f.channel().closeFuture().sync();
            System.out.println("Netty客户端关闭!");
        } finally {
            group.shutdownGracefully();
            System.out.println("netty客户端退出~");
        }
    }

    public static class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            HttpXmlRequest request = new HttpXmlRequest(null,null);
            ctx.writeAndFlush(request);
            System.out.println("Netty Client channel active ending...");
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
            System.out.println("Netty Client接收到消息头:"+msg.getResponse().headers().names());
            System.out.println("Netty Client接收到消息:"+msg.getResponse());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println(cause.getStackTrace());
            ctx.close();
        }
    }

}
