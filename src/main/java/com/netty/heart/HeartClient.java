package com.netty.heart;

import com.netty.chat.GroupChatClient;
import com.netty.chat.MyGroupChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class HeartClient {

    private String net_ip;
    private int net_port;

    public HeartClient(String net_ip, int net_port) {
        this.net_ip = net_ip;
        this.net_port = net_port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .handler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel sc) throws Exception {
                             ChannelPipeline pipeline = sc.pipeline();
                             // 向pipeline中加入解码器
                             pipeline.addLast("decoder", new StringDecoder());
                             // 向pipeline中加入编码器
                             pipeline.addLast("encoder", new StringEncoder());
                             pipeline.addLast("MyGroupChatClientHandler", new MyGroupChatClientHandler());
                         }
                     });

            ChannelFuture channelFuture = bootstrap.connect(net_ip, net_port)
                                                   .sync();

            Channel channel = channelFuture.channel();
            System.out.println("我是【客户端】：" + channel.localAddress() + ",我来了");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String send_msg = scanner.nextLine();
                channel.writeAndFlush(send_msg + "\r\n");
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new GroupChatClient("127.0.0.1", 8848).run();
    }
}
