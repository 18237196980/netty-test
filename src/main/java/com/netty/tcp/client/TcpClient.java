package com.netty.tcp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * tcp粘包、拆包解决方案（自定义协议对象）
 * client端
 */
public class TcpClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 不是ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .handler(new MyTcpClientChannelInitializer());

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668);
            channelFuture.channel()
                         .closeFuture()
                         .sync();
        } finally {
            group.shutdownGracefully();
        }

    }

}
