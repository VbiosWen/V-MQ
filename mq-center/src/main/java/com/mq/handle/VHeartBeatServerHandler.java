package com.mq.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 2:07 PM 2018/12/22
 * @Modified By:
 */
public class VHeartBeatServerHandler extends ChannelInboundHandlerAdapter {

  private int loss_connect_time = 0;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println(ctx.channel().remoteAddress() + " server:" + msg.toString());
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent event = (IdleStateEvent) evt;
      if (event.state() == IdleState.READER_IDLE) {
        loss_connect_time++;
        System.out.println("接受消息超时");
        if (loss_connect_time > 10) {
          System.out.println("关闭不活动的链接");
          ctx.channel().close();
        }
      } else {
        super.userEventTriggered(ctx, evt);
      }
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
