package com.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 群聊服务器handler
 */
public class MyGroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 监听到客户端加入群聊
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【客户端】:" + channel.remoteAddress() + ",加入了群聊\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【客户端】:" + channel.remoteAddress() + ",离开了群聊\n");
    }

    // channel上线了
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户端】:" + ctx.channel()
                                         .remoteAddress() + ",上线了\n");
    }

    // channel下线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户端】:" + ctx.channel()
                                         .remoteAddress() + ",下线了\n");
    }

    // 服务器端独取客户端发送的消息后，转发消息给其他已连接的客户端
    @Override
    protected void channelRead0(ChannelHandlerContext ch, String msg) throws Exception {
        final Channel curr_channel = ch.channel();

        channelGroup.forEach(c -> {
            if (c != curr_channel) {
                c.writeAndFlush("【客户端】:" + curr_channel.remoteAddress() + ",发送了:" + msg + "\n");
            } else {
                curr_channel.writeAndFlush("自己发送了消息：" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
