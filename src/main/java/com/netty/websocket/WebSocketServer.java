package com.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * websocket服务端
 */
public class WebSocketServer {

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(3);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(group, work)
                     .channel(NioServerSocketChannel.class)
                     .option(ChannelOption.SO_BACKLOG, 128)
                     .childOption(ChannelOption.SO_KEEPALIVE, true)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         protected void initChannel(SocketChannel sc) throws Exception {
                             ChannelPipeline pipeline = sc.pipeline();

                             // 基于http协议，使用http的编解码器
                             pipeline.addLast(new HttpServerCodec());
                             // http数据传输过程是分段的，HttpObjectAggregator可以使多段聚合
                             pipeline.addLast(new HttpObjectAggregator(8192));
                             // WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议,保持长连接。客户端请求是ws://localhost:6668/hello
                             pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                             // 自定义handler
                             pipeline.addLast(new MyWebSocketHandler());
                         }
                     });

            System.out.println("server端 准备好了~");

            ChannelFuture channelFuture = bootstrap.bind(6668)
                                                   .sync();

            // 对关闭通道进行监听
            channelFuture.channel()
                         .closeFuture()
                         .sync();
        } finally {
            group.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

}
