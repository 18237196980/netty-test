package com.netty.test1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {

        /*ctx.channel().eventLoop().execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端1~", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/

        ctx.channel()
           .eventLoop()
           .schedule(new Runnable() {
               public void run() {
                   try {
                       // Thread.sleep(3 * 1000);
                       ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端1~", CharsetUtil.UTF_8));
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           }, 5, TimeUnit.SECONDS);

        /*System.out.println("server端处理任务的线程:" + Thread.currentThread()
                                                     .getName());
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + ctx.channel()
                                         .remoteAddress());*/
    }

    // 在服务端读取完消息，再发送给客户端消息
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端2~", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server端Handler 发生异常~");
        cause.printStackTrace();
        ctx.close();
    }
}
