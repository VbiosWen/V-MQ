package com.rocket.common.handle;

import com.rocket.common.message.Header;
import com.rocket.common.message.MessageType;
import com.rocket.common.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Objects;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 7:37 PM 2018/11/26
 * @Modified By:
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {


  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    NettyMessage message = buildLoginReq();
    ctx.writeAndFlush(message);
    System.out.println("客户端发送握手请求 "+message);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    NettyMessage message= (NettyMessage) msg;
    if(!Objects.isNull(message)&&message.getHeader().getType()==MessageType.LOGIN_RESP){
      if(message.getBody()!=null){
        String loginResult=message.getBody().toString();
        if("login_ok".equals(loginResult)){
          System.out.println("Login is success :"+message);
          //传输给下一个handler
          ctx.fireChannelRead(msg);
        }else{
          //握手失败,关闭连接
          ctx.close();
          System.out.println("握手失败,关闭连接");
        }
      }else{
        ctx.close();
        System.out.println("握手失败,关闭连接");
      }
    }else{
      //如果不是握手应答消息,传输给下一个handler
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

  private NettyMessage buildLoginReq(){
    NettyMessage message=new NettyMessage();
    Header header=new Header();
    header.setType(MessageType.LOGIN_REQ);
    message.setHeader(header);
    return message;
  }
}
