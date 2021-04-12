package com.netty.tcp;

import com.netty.tcp.TcpXieYiModel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyTcpDecoder extends ReplayingDecoder<TcpXieYiModel> {
    @Override
    protected void decode(ChannelHandlerContext ch, ByteBuf byteBuf, List<Object> out) throws Exception {
        System.out.println("解码器路过...");
        // 需要将得到的二进制字节码转换为->TcpXieYiModel对象
        int len = byteBuf.readInt();
        byte[] con = new byte[len];
        byteBuf.readBytes(con); // 将byteBuf中数据读取到数组con中

        TcpXieYiModel model = new TcpXieYiModel(len, con);
        out.add(model); // 交给下一个handler处理

    }
}
