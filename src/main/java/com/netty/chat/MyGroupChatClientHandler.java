package com.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 群聊客户端 收到信息
 */
public class MyGroupChatClientHandler extends SimpleChannelInboundHandler<String> {

    // 客户端收到服务器端转发的消息
    @Override
    protected void channelRead0(ChannelHandlerContext ch, String msg) throws Exception {
        System.out.println(msg + "\n");
    }
}
