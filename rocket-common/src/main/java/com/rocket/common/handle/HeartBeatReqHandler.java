package com.rocket.common.handle;

import com.rocket.common.message.Header;
import com.rocket.common.message.MessageType;
import com.rocket.common.message.NettyMessage;
import com.rocket.common.task.HeartBeatTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:02 PM 2018/11/26
 * @Modified By:
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {

  private volatile ScheduledFuture<?> heartBeat;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    NettyMessage message = (NettyMessage) msg;
    Header header = message.getHeader();
    if (header != null) {
      if (header.getType() == MessageType.LOGIN_RESP) {
        if (heartBeat == null) {
          heartBeat = ctx.executor()
              .scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5, TimeUnit.SECONDS);
        }
      } else if (header.getType() == MessageType.HEARTBEAT_RESP) {
        System.out.println("客户端收到服务端的心跳检测消息:" + message);
      } else {
        ctx.fireChannelRead(msg);
      }
    }else{
      ctx.fireExceptionCaught(new Exception("没有header"));
    }
  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    try {
      ctx.fireExceptionCaught(cause);
    }finally {
      closeHeartBeat();
    }
  }

  private void closeHeartBeat() {
    if(heartBeat!=null){
      heartBeat.cancel(true);
      heartBeat=null;
    }
  }
}
