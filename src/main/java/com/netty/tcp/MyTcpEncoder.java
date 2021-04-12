package com.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyTcpEncoder extends MessageToByteEncoder<TcpXieYiModel> {
    @Override
    protected void encode(ChannelHandlerContext ch, TcpXieYiModel msg, ByteBuf byteBuf) throws Exception {
        System.out.println("编码器路过...");
        byteBuf.writeInt(msg.getLen());
        byteBuf.writeBytes(msg.getContent());
    }
}
