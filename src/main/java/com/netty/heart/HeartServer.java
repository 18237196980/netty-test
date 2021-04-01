package com.netty.heart;

import com.netty.http.MyChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class HeartServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(3);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(group, work)
                     .channel(NioServerSocketChannel.class)
                     .handler(new LoggingHandler(LogLevel.INFO))
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel sc) throws Exception {
                             ChannelPipeline pipeline = sc.pipeline();

                             pipeline.addLast(new IdleStateHandler(3, 5, 7));
                             pipeline.addLast("heartServerHandler", new HeartServerHandler());

                         }
                     });

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
