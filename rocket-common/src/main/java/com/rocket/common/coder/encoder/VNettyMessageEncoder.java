package com.rocket.common.coder.encoder;

import com.rocket.common.message.VMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import java.util.Objects;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 7:23 PM 2018/12/20
 * @Modified By:
 */
public class VNettyMessageEncoder extends MessageToMessageEncoder<VMessage> {

  @Override
  protected void encode(ChannelHandlerContext ctx, VMessage msg, List<Object> out)
      throws Exception {
    if(Objects.isNull(msg)){
      throw new NullPointerException("msg is null");
    }
    ByteBuf buffer = Unpooled.buffer();
    buffer.writeInt(msg.getHeader().getHeadPrefix());
    buffer.writeInt(msg.getHeader().getLength());
    buffer.writeLong(msg.getHeader().getSessionID());
    buffer.writeByte(msg.getHeader().getPriority());
    buffer.writeByte(msg.getHeader().getType());
    buffer.writeInt(msg.getBody().length);
    if(Objects.nonNull(msg.getBody())){
      buffer.writeBytes(msg.getBody());
    }
    int readableBytes = buffer.readableBytes();
    buffer.setInt(4,readableBytes);
    out.add(buffer);
  }
}
