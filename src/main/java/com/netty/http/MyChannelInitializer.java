package com.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel sc) throws Exception {
        // 管道中加入handler
        ChannelPipeline pipeline = sc.pipeline();

        // 加入netty提供的http编解码器HttpServerCodec
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 加入自定义handler
        pipeline.addLast("MyHttpServerProcessHandler", new HttpServerProcessHandler());

    }
}
