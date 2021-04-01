package com.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpServerProcessHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 处理浏览器发送的请求，并回复浏览器
    protected void channelRead0(ChannelHandlerContext ch, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {

            String uri = ((HttpRequest) msg).uri();

            if (uri.equals("/favicon.ico")) {
                System.out.println("请求地址为icon");
                return;
            }

            System.out.println("客户端请求类型:" + msg.getClass());
            System.out.println("客户端请求类型:" + msg.getClass());
            System.out.println("客户端请求地址:" + ch.channel()
                                              .remoteAddress());

            // 构造响应
            ByteBuf content = Unpooled.copiedBuffer("abc你好啊", CharsetUtil.UTF_8);

            FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            resp.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            resp.headers()
                .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ch.writeAndFlush(resp);

        }

    }
}
