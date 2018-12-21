package com.rocket.common.task;

import com.rocket.common.message.Header;
import com.rocket.common.message.MessageType;
import com.rocket.common.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:05 PM 2018/11/26
 * @Modified By:
 */
public class HeartBeatTask implements Runnable {

  private final ChannelHandlerContext ctx;

  public HeartBeatTask(ChannelHandlerContext ctx) {
    this.ctx = ctx;
  }

  @Override
  public void run() {
    ctx.writeAndFlush(buildHeartBeat());
    System.out.println("客户端发送心跳检测消息");
  }

  private NettyMessage buildHeartBeat() {
    NettyMessage message=new NettyMessage();
    Header header=new Header();
    header.setType(MessageType.HEARTBEAT_REQ);
    message.setHeader(header);
    return message;
  }
}
