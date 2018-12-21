package com.rocket.common.handle;

import com.rocket.common.message.Header;
import com.rocket.common.message.MessageType;
import com.rocket.common.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:14 PM 2018/11/26
 * @Modified By:
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    NettyMessage message = (NettyMessage) msg;
    if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ) {
      System.out.println("收到客户端的心跳检测消息");
      ctx.writeAndFlush(buildHeartBeat());
      System.out.println("发送心跳检测消息到客户端");
    } else {
      ctx.fireChannelRead(msg);
    }
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.fireExceptionCaught(cause);
  }


  private NettyMessage buildHeartBeat() {
    NettyMessage heartBeat=new NettyMessage();
    Header header=new Header();
    header.setType(MessageType.HEARTBEAT_RESP);
    heartBeat.setHeader(header);
    heartBeat.setBody(null);
    return heartBeat;
  }
}
