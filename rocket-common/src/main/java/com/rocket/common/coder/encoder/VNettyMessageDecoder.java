package com.rocket.common.coder.encoder;

import com.rocket.common.message.VMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java.util.Objects;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 7:38 PM 2018/12/20
 * @Modified By:
 */
public class VNettyMessageDecoder extends LengthFieldBasedFrameDecoder {


  public VNettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
    super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    ByteBuf frame = (ByteBuf) super.decode(ctx, in);
    if(Objects.isNull(frame)){
      return null;
    }
    VMessage message=new VMessage();
    message.getHeader().setHeadPrefix(frame.readInt());
    message.getHeader().setLength(frame.readInt());
    message.getHeader().setSessionID(frame.readLong());
    message.getHeader().setPriority(frame.readByte());
    message.getHeader().setType(frame.readByte());
    byte[] body=new byte[frame.readInt()];
    frame.readBytes(body);
    message.setBody(body);
    return message;
  }
}
