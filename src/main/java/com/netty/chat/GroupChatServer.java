package com.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 群聊服务器端
 */
public class GroupChatServer {

    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap strap = new ServerBootstrap();

            strap.group(group, work)
                 .channel(NioServerSocketChannel.class)
                 .option(ChannelOption.SO_BACKLOG, 128)
                 .childOption(ChannelOption.SO_KEEPALIVE, true)
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                     protected void initChannel(SocketChannel sc) throws Exception {
                         ChannelPipeline pipeline = sc.pipeline();

                         // 向pipeline中加入解码器
                         pipeline.addLast("decoder", new StringDecoder());
                         // 向pipeline中加入编码器
                         pipeline.addLast("encoder", new StringEncoder());
                         pipeline.addLast("MyGroupChatServerHandler", new MyGroupChatServerHandler());
                     }
                 });

            System.out.println("server服务器端准备好了~");

            ChannelFuture channelFuture = strap.bind(port)
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

    public static void main(String[] args) throws Exception {
        new GroupChatServer(6668).run();
    }

}
