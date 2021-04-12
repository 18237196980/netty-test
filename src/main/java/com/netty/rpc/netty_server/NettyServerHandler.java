package com.netty.rpc.netty_server;

import com.netty.rpc.service.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端handler接收到消息：" + msg.toString());
        String param = msg.toString();
        if (param.startsWith("abc#")) {
            String res = new HelloServiceImpl().getMsg(param.substring(param.lastIndexOf("#") + 1));
            ctx.writeAndFlush(res);
        } else {
            System.out.println("参数格式错误");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
