package com.rocket.common.coder.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.Marshaller;


/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 11:44 PM 2018/11/25
 * @Modified By:
 */
public class NettyMarshallingEncoder extends MarshallingEncoder {

  private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

  Marshaller marshaller;


  /**
   * Creates a new encoder.
   *
   * @param provider the {@link MarshallerProvider} to use
   */
  public NettyMarshallingEncoder(MarshallerProvider provider) {
    super(provider);
  }

  @Override
  public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
    super.encode(ctx, msg, out);
  }
}
