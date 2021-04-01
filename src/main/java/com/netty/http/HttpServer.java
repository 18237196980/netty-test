package com.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * http server端
 */
public class HttpServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(3);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(group, work)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new MyChannelInitializer());

            System.out.println("server端 准备好了~");

            ChannelFuture channelFuture = bootstrap.bind(8848)
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
