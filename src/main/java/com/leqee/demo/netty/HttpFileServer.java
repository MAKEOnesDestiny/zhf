package com.leqee.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.apachecommons.CommonsLog;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

@CommonsLog
public class HttpFileServer {

    public static class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
            if (!request.getDecoderResult().isSuccess()) {
                //请求解码失败
                sendError(ctx, BAD_REQUEST);
                log.error("请求解码失败");
                return;
            }
            if (request.getMethod() != HttpMethod.GET) {
                sendError(ctx, METHOD_NOT_ALLOWED);
                log.error("请求方式需要为GET方法");
                return;
            }
            final String uri = request.getUri();
            final String path = sanitizeUrl(uri);
            File file = new File(path);
            if (file.isHidden() || !file.exists()) {
                sendError(ctx, NOT_FOUND);
                log.error(file.getAbsolutePath() + "文件不存在或者被隐藏");
                return;
            } else if (file.isDirectory()) {
                //请求的文件是目录
                sendListing(ctx, file);
                log.info("http读取目录:" + file.getAbsolutePath());
            }

            RandomAccessFile accessFile = new RandomAccessFile(file, "r"); //只读打开
            long fileLength = accessFile.length();

            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, OK);
            response.headers().set(CONTENT_TYPE, "application/octet-stream")
                    .set(CONTENT_LENGTH, fileLength);
            if (request.headers().get("Connection").equalsIgnoreCase("keep-alive")) {
                response.headers().set(CONNECTION, KEEP_ALIVE);  //长连接
            }
            ctx.write(response);
            ChannelFuture future = ctx.write(new ChunkedFile(accessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
            ctx.flush();
            future.addListener(new ChannelProgressiveFutureListener() {
                @Override
                public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                    System.out.println("operationProgressed");
                }

                @Override
                public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                    System.out.println("operationComplete");
                }
            });

        }

        //解析file path
        public String sanitizeUrl(String uri) {
            try {
                URLDecoder.decode(uri, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getStackTrace());
            }
            uri = uri.replace('/', File.separatorChar);
            return System.getProperty("user.dir") + File.separator + uri;
        }

        public void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

        public void sendListing(ChannelHandlerContext ctx, File dir) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK);
            response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>");
            sb.append("<ul>");
            //父级目录
            sb.append("<li>链接:<a href=\"" + ".." + "\">..</a></li>\r\n");
            for (File f : dir.listFiles()) {
                sb.append("<li>链接:<a href=\"" + trimPrefix("E:\\workspace\\demo", f.getAbsolutePath()) + "\">" + f.getName() + "</a></li>");
            }
            sb.append("</ul></body></html>\r\n");
            ByteBuf buffer = Unpooled.copiedBuffer(sb, CharsetUtil.UTF_8);
            response.content().writeBytes(buffer);
            buffer.release();
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE); //必须要加Listener，不然一直再pending
        }

        public static String trimPrefix(String prefix, String raw) {
            String result = null;
            if (raw.startsWith(prefix)) {
                result = raw.substring(prefix.length());
            }
            return result;
        }

    }

    public static void main(String[] args) {
        //用于接收请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于处理请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536)); //http encoder解码后为多个request
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler()); //异步发送大码流
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler());
                        }
                    });
            ChannelFuture future = b.bind("127.0.0.1", 8080).sync();
            log.info("HTTP文件目录服务器启动，网址是:{}" + 8080);
            future.channel().closeFuture().sync(); //等待关闭
        } catch (InterruptedException e) {
            log.error(e.getStackTrace());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
