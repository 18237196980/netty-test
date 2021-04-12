package com.netty.tcp.client;

import com.netty.tcp.TcpXieYiModel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyTcpClientHandler extends SimpleChannelInboundHandler<TcpXieYiModel> {

    // 读取服务器端回复的消息
    @Override
    protected void channelRead0(ChannelHandlerContext ch, TcpXieYiModel model) throws Exception {
        System.out.println("读取服务器端回复的消息如下：");
        System.out.println("回复长度：" + model.getLen() + "  " + "回复内容：" + new String(model.getContent(), Charset.forName("utf-8")));
        System.out.println("=============[]==============");
    }

    // 向服务器端发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("准备发送数据...");
        String conStr = "张三";
        for (int i = 0; i < 2; i++) {
            byte[] content = conStr.getBytes(Charset.forName("utf-8"));
            int len = conStr.getBytes(Charset.forName("utf-8")).length;
            TcpXieYiModel model = new TcpXieYiModel(len, content);
            ctx.writeAndFlush(model);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client端发生异常...");
        cause.printStackTrace();
        ctx.close();
    }
}
