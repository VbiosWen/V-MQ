package com.rocket.common.handle;

import com.rocket.common.message.Header;
import com.rocket.common.message.MessageType;
import com.rocket.common.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 7:42 PM 2018/11/26
 * @Modified By:
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {


  private String[] writeList = {"/127.0.0.1"};

  private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();


  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    NettyMessage message = (NettyMessage) msg;
    if (!Objects.isNull(message) && message.getHeader().getType() == MessageType.LOGIN_REQ) {
      System.out.println("收到客户端登录请求 :" + message);
      NettyMessage loginResp = null;
      String nodeIndex = ctx.channel().remoteAddress().toString().split(":")[0];
      if (nodeCheck.containsKey(nodeIndex)) {
        loginResp = buildLoginResponse("login_repeat");
      } else {
        boolean tag = false;
        for (String ip : writeList) {
          if (ip.equals(nodeIndex)) {
            tag = true;
            break;
          }
        }
        if (tag) {
          nodeCheck.put(nodeIndex, true);
          loginResp = buildLoginResponse("login_ok");
        } else {
          loginResp = buildLoginResponse("login_fail");
        }
        ctx.writeAndFlush(loginResp);
        System.out.println("send login resp is :" + loginResp);
      }
    } else {
      ctx.fireChannelRead(msg);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    try {
      ctx.fireExceptionCaught(cause);
    }finally {
      nodeCheck.remove("/127.0.0.1");
    }
  }

  private NettyMessage buildLoginResponse(String result) {
    NettyMessage message = new NettyMessage();
    Header header = new Header();
    header.setType(MessageType.LOGIN_RESP);
    message.setHeader(header);
    message.setBody(result);
    return message;
  }

}
