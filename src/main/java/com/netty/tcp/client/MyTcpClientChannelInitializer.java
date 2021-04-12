package com.netty.tcp.client;

import com.netty.tcp.MyTcpDecoder;
import com.netty.tcp.MyTcpEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyTcpClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel sc) throws Exception {

        ChannelPipeline pipeline = sc.pipeline();
        pipeline.addLast(new MyTcpEncoder());
        pipeline.addLast(new MyTcpDecoder()); // 客户端读取服务器端返回的信息时，需要解码器
        pipeline.addLast(new MyTcpClientHandler());

    }
}
