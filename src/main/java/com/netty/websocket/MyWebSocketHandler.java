package com.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;


/**
 * 自定义websocket handler
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 读取channel中数据
    @Override
    protected void channelRead0(ChannelHandlerContext ch, TextWebSocketFrame msg) throws Exception {
        Channel channel = ch.channel();
        System.out.println("服务器收到消息：" + msg.text());

        channel.writeAndFlush(new TextWebSocketFrame("服务器收到的信息:" + LocalDateTime.now() + ":" + msg.text()));
    }

    // 建立连接
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器和客户端建立连接...channel:" + ctx.channel()
                                                         .id()
                                                         .asLongText());
    }

    // 连接断开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器和客户端连接断开...channel:" + ctx.channel()
                                                         .id()
                                                         .asLongText());
    }

    // 发生异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常...");
        ctx.close();
    }
}
