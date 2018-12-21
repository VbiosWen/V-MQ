package com.mq.binary_process.decoder;

import com.mq.binary_process.factory.SerialTypeFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:54 PM 2018/12/21
 * @Modified By:
 */
public class NettyDecode extends LengthFieldBasedFrameDecoder {

  private final SerialTypeFactory serialTypeFactory;

  private static final int FRAME_MAX_LENGTH = Integer
      .parseInt(System.getProperty("com.mq.common.frameMaxLength", "16777216"));

  public NettyDecode() {
    super(FRAME_MAX_LENGTH, 0, 4, 0, 4);
    this.serialTypeFactory = new SerialTypeFactory();
  }


  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    ByteBuf frame = null;
    try {
      frame = (ByteBuf) super.decode(ctx, in);
      if (Objects.isNull(frame)) {
        return null;
      }
      ByteBuffer byteBuffer = frame.nioBuffer();
      return serialTypeFactory.decode(byteBuffer);
    } catch (Exception ex) {
      ctx.close();
    } finally {
      if (Objects.nonNull(frame)) {
        frame.release();
      }
    }
    return null;
  }
}
