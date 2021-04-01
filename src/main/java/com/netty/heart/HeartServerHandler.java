package com.netty.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleState = (IdleStateEvent) evt;
            String state_type = null;

            switch (idleState.state()) {
                case READER_IDLE:
                    state_type = "读空闲";
                    break;
                case WRITER_IDLE:
                    state_type = "写空闲";
                    break;
                case ALL_IDLE:
                    state_type = "读写空闲";
                    break;
                default:
                    break;
            }
            System.out.println(ctx.channel()
                                  .remoteAddress() + "超时【" + state_type + "】");
        }
    }
}
