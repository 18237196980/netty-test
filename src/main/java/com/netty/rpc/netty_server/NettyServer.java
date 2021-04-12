package com.netty.rpc.netty_server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {

    public static void serverStart(String address, int port) throws Exception {
        serverStart0(address, port);
    }

    public static void serverStart0(String address, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(8);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(group, work)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel sc) throws Exception {
                             ChannelPipeline pipeline = sc.pipeline();
                             pipeline.addLast(new StringDecoder());
                             pipeline.addLast(new StringEncoder());

                             pipeline.addLast(new NettyServerHandler());
                         }
                     });

            System.out.println("server端 准备好了~");
            System.out.println();
            ChannelFuture channelFuture = bootstrap.bind(address, port)
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
