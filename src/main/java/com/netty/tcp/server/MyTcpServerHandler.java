package com.netty.tcp.server;

import com.netty.tcp.TcpXieYiModel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyTcpServerHandler extends SimpleChannelInboundHandler<TcpXieYiModel> {
    private int count;

    // 读取客户端发送的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ch, TcpXieYiModel msg) throws Exception {
        System.out.println("服务端读取到客户端消息如下：");
        System.out.println("长度：" + msg.getLen() + "  " + "内容：" + new String(msg.getContent(), Charset.forName("utf-8")) + "  次数：" + (++count));

        System.out.println("-------------------------------------");

        // 向客户端回复消息
        String resp = "今天晴天";
        byte[] content = resp.getBytes(Charset.forName("utf-8"));
        int len = resp.getBytes(Charset.forName("utf-8")).length;

        TcpXieYiModel model = new TcpXieYiModel(len, content);
        ch.writeAndFlush(model);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server端发生异常...");
        cause.printStackTrace();
        ctx.channel();
    }
}
