package com.mq.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 9:57 PM 2018/12/22
 * @Modified By:
 */
public class VHeatBeatBrokerHandle extends ChannelInboundHandlerAdapter {

  private static final ByteBuf HEART_BEAT_SEQUENCE= Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HeartBeat",
      Charset.forName("UTF-8")));

  private static final int TRY_TIMES=10;

  private int currentTime=0;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("激活时间是:"+LocalDateTime.now());
    System.out.println("链接已经激活");
    ctx.fireChannelActive();
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("停止时间是:"+LocalDateTime.now());
    System.out.println("关闭连接");
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    System.out.println("当前轮询时间:"+LocalDateTime.now());
    if(evt instanceof IdleStateEvent){
      IdleStateEvent event= (IdleStateEvent) evt;
      if(event.state()== IdleState.WRITER_IDLE){
        if(currentTime<=TRY_TIMES){
          System.out.println("current Time :"+ currentTime);
          currentTime++;
          ctx.channel().writeAndFlush(HEART_BEAT_SEQUENCE.duplicate());
        }
      }
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    String message= (String) msg;
    System.out.println("message");
    if(message.equals("HeartBeat")){
      ctx.write("has read message from server");
      ctx.flush();
    }
    ReferenceCountUtil.release(msg);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
