package com.netty.tcp.server;

import com.netty.tcp.MyTcpDecoder;
import com.netty.tcp.MyTcpEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyTcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();
        pipeline.addLast(new MyTcpDecoder());
        pipeline.addLast(new MyTcpEncoder()); // 服务端向客户端回复信息时，需要编码器
        pipeline.addLast(new MyTcpServerHandler());


    }
}
