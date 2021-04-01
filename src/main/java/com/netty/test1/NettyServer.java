package com.netty.test1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty入门服务端
 */
public class NettyServer {

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
                             sc.pipeline()
                               .addLast(new NettyServerHandler());
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
